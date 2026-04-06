package com.stage.swift.service.mx.impl;

import com.stage.swift.entity.VirementRecu;
import com.stage.swift.helpers.AckHelper;
import com.stage.swift.repository.VirementRecuRepository;
import com.stage.swift.service.entity.VirementRecuService;
import com.stage.swift.service.mx.SopRecuService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.stage.swift.helpers.MxMapperHelper.truncate;

/**
 * Flux reçu OUT_SOP : trouve VirementRecu par référence (MsgId / GrpHdr) et met à jour le statut (traité).
 */
@Service
public class SopRecuServiceImpl implements SopRecuService {

    private static final Logger log = LoggerFactory.getLogger(SopRecuServiceImpl.class);

    private final VirementRecuRepository virementRecuRepository;
    private final VirementRecuService virementRecuService;

    public SopRecuServiceImpl(VirementRecuRepository virementRecuRepository,
                              VirementRecuService virementRecuService) {
        this.virementRecuRepository = virementRecuRepository;
        this.virementRecuService = virementRecuService;
    }

    @Override
    @Transactional
    public void processSopRecu(String xmlContent) {
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
                    if (currentStatut.equalsIgnoreCase(statutRapprochement)) {
                        unchanged++;
                        continue;
                    }
                    v.setStatutRecu(statutRapprochement);
                    virementRecuService.save(v);
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
}
