package com.stage.swift.service.mx.impl;

import com.stage.swift.entity.VirementRecu;
import com.stage.swift.helpers.AckHelper;
import com.stage.swift.entity.Sop;
import com.stage.swift.repository.MessageRecuRepository;
import com.stage.swift.repository.SopRepository;
import com.stage.swift.repository.VirementRecuRepository;
import com.stage.swift.service.entity.VirementRecuService;
import com.stage.swift.service.mx.SopRecuService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

import static com.stage.swift.helpers.MxMapperHelper.truncate;

/**
 * Flux reçu OUT_SOP : trouve VirementRecu par référence (MsgId / GrpHdr) et met à jour le statut (traité).
 */
@Service
public class SopRecuServiceImpl implements SopRecuService {

    private static final Logger log = LoggerFactory.getLogger(SopRecuServiceImpl.class);

    private static final Pattern SOP_FOLDER_PATTERN = Pattern.compile("(?i)(?:^|[\\\\/])SOP_(\\d+)(?:[_-]([^\\\\/]+))?(?:[\\\\/]|$)");

    private final VirementRecuRepository virementRecuRepository;
    private final MessageRecuRepository messageRecuRepository;
    private final SopRepository sopRepository;
    private final VirementRecuService virementRecuService;

    public SopRecuServiceImpl(VirementRecuRepository virementRecuRepository,
                              MessageRecuRepository messageRecuRepository,
                              SopRepository sopRepository,
                              VirementRecuService virementRecuService) {
        this.virementRecuRepository = virementRecuRepository;
        this.messageRecuRepository = messageRecuRepository;
        this.sopRepository = sopRepository;
        this.virementRecuService = virementRecuService;
    }

    @Override
    @Transactional
    public void processSopRecu(String xmlContent, String sourceFilePath) {
        if (xmlContent == null || xmlContent.trim().isEmpty()) {
            throw new IllegalArgumentException("Contenu SOP recu vide");
        }
        // Statut réseau lu dans <Saa:NetworkDeliveryStatus> (ACK / NACK / Accepted / Rejected, ...)
        String rawStatus = AckHelper.extractStatus(xmlContent);
        String normalized = AckHelper.normalizeStatutSwift(rawStatus);
        // Pour le rapprochement reçu : ACK => RAPPROCHE, sinon NON_RAPPROCHE
        String statutRapprochement = "ACK".equalsIgnoreCase(normalized) ? "RAPPROCHE" : "NON_RAPPROCHE";

        List<String> refs = AckHelper.extractAllReferencesOrdered(xmlContent);
        if (refs == null || refs.isEmpty()) {
            throw new IllegalArgumentException("SOP recu: aucune référence (MsgId / GrpHdr) trouvée");
        }
        Long folderSopId = resolveSopIdFromOutSopPath(sourceFilePath);
        for (String ref : refs) {
            String refTrim = truncate(ref != null ? ref.trim() : "", 35);
            if (refTrim.isEmpty()) continue;
            List<VirementRecu> list = virementRecuRepository.findByReferenceTrimmed(refTrim);
            if (list.isEmpty()) {
                list = virementRecuRepository.findByReference(refTrim);
            }
            if (!list.isEmpty()) {
                int updated = 0;
                int unchanged = 0;
                for (VirementRecu v : list) {
                    String currentStatut = v.getStatutRecu() != null ? v.getStatutRecu().trim() : "";
                    Long currentSopId = v.getIdSop();
                    if (folderSopId != null && !folderSopId.equals(currentSopId)) {
                        // IMPORTANT: parent (virement_recu) first, then child (message_recu)
                        // to keep composite FK consistency during the same transaction.
                        virementRecuRepository.updateSopByIdVrtRecu(v.getIdVrtRecu(), folderSopId);
                        messageRecuRepository.updateSopByIdVrtRecu(v.getIdVrtRecu(), folderSopId);
                        log.info("OUT_SOP: id_vrt_recu={} SOP mis à jour {} -> {} depuis dossier", v.getIdVrtRecu(), currentSopId, folderSopId);
                    }
                    if (currentStatut.equalsIgnoreCase(statutRapprochement)) {
                        unchanged++;
                        continue;
                    }
                    // PK de VirementRecu est composite (inclut id_sop). On évite toute mutation
                    // d'entité managée et on met à jour en SQL direct pour rester stable.
                    virementRecuRepository.updateStatutRapprochementByIdVrtRecu(v.getIdVrtRecu(), statutRapprochement);
                    updated++;
                }
                if (updated == 0) {
                    log.info("OUT_SOP ignoré (idempotent): reference={}, statut déjà ({}), {} virement(s) inchangé(s)",
                            refTrim, statutRapprochement, unchanged);
                } else {
                    log.info("OUT_SOP: reference={}, colonne statut mise à jour ({}), {} virement(s) recu, {} inchangé(s)",
                            refTrim, statutRapprochement, updated, unchanged);
                }
                return;
            }
        }
        throw new IllegalStateException("SOP recu: aucune référence trouvée ne correspond à un VirementRecu en base (refs essayées: " + refs.size() + ")");
    }

    private Long resolveSopIdFromOutSopPath(String sourceFilePath) {
        Optional<Long> sopFromPath = extractSopIdFromPath(sourceFilePath);
        if (sopFromPath.isPresent()) {
            Long sopId = sopFromPath.get();
            if (sopRepository.findById(sopId).isPresent()) {
                return sopId;
            }
            Sop sop = new Sop();
            sop.setId(sopId);
            sop.setLibelleSop(extractSopLibelleFromPath(sourceFilePath, sopId));
            sopRepository.save(sop);
            log.warn("OUT_SOP: SOP auto-créé depuis dossier: id={}, libelle='{}'", sopId, sop.getLibelleSop());
            return sopId;
        }
        Optional<String> sopFolderLabel = extractSopFolderLabelFromPath(sourceFilePath);
        if (!sopFolderLabel.isPresent()) {
            return null;
        }
        String folderLabel = sopFolderLabel.get().trim();
        return sopRepository.findByLibelleSopNormalized(folderLabel)
                .map(Sop::getId)
                .orElseGet(() -> {
                    Long newId = sopRepository.nextIdSop();
                    Sop sop = new Sop();
                    sop.setId(newId);
                    sop.setLibelleSop(folderLabel);
                    sopRepository.save(sop);
                    log.warn("OUT_SOP: SOP auto-créé depuis nom dossier: id={}, libelle='{}'", newId, folderLabel);
                    return newId;
                });
    }

    private Optional<Long> extractSopIdFromPath(String sourceFilePath) {
        if (sourceFilePath == null || sourceFilePath.trim().isEmpty()) {
            return Optional.empty();
        }
        java.util.regex.Matcher matcher = SOP_FOLDER_PATTERN.matcher(sourceFilePath);
        if (!matcher.find()) {
            return Optional.empty();
        }
        try {
            return Optional.of(Long.parseLong(matcher.group(1)));
        } catch (NumberFormatException ignored) {
            return Optional.empty();
        }
    }

    private String extractSopLibelleFromPath(String sourceFilePath, Long sopId) {
        if (sourceFilePath != null) {
            java.util.regex.Matcher matcher = SOP_FOLDER_PATTERN.matcher(sourceFilePath);
            if (matcher.find()) {
                String labelPart = matcher.group(2);
                if (labelPart != null && !labelPart.trim().isEmpty()) {
                    return labelPart.replace('_', ' ').trim();
                }
            }
        }
        return "SOP " + sopId;
    }

    private Optional<String> extractSopFolderLabelFromPath(String sourceFilePath) {
        if (sourceFilePath == null || sourceFilePath.trim().isEmpty()) {
            return Optional.empty();
        }
        String normalized = sourceFilePath.replace('\\', '/');
        String[] segments = normalized.split("/");
        for (int i = 0; i < segments.length - 2; i++) {
            String current = segments[i];
            String child = segments[i + 1];
            if (current == null || child == null || current.trim().isEmpty()) {
                continue;
            }
            String upperChild = child.trim().toUpperCase();
            if ("OUT_SOP".equals(upperChild) || "IN_SOP".equals(upperChild)) {
                return Optional.of(current.trim().replace('_', ' '));
            }
        }
        return Optional.empty();
    }
}
