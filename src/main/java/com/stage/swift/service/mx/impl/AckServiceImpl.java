package com.stage.swift.service.mx.impl;

import com.stage.swift.dto.mx.AckDto;
import com.stage.swift.entity.VirementEmis;
import com.stage.swift.helpers.AckHelper;
import com.stage.swift.repository.VirementEmisRepository;
import com.stage.swift.service.mx.AckService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.stage.swift.helpers.MxMapperHelper.truncate;


@Service
public class AckServiceImpl implements AckService {

    private static final Logger log = LoggerFactory.getLogger(AckServiceImpl.class);

    private final VirementEmisRepository virementEmisRepository;

    public AckServiceImpl(VirementEmisRepository virementEmisRepository) {
        this.virementEmisRepository = virementEmisRepository;
    }

    @Override
    @Transactional
    public void processAckFile(String ackContent) {
        if (ackContent == null || ackContent.trim().isEmpty()) {
            throw new IllegalArgumentException("Contenu .ack vide");
        }
        boolean anyUpdated = false;
        String[] pdus = ackContent.split("</Saa:DataPDU>");
        for (String pdu : pdus) {
            if (pdu == null || pdu.trim().isEmpty() || pdu.matches("[\\r\\n\\s]+")) continue;
            String fullPdu = pdu.trim().endsWith("</Saa:DataPDU>") ? pdu.trim() : pdu.trim() + "</Saa:DataPDU>";
            List<String> refs = AckHelper.extractAllReferencesOrdered(fullPdu);
            if (refs == null || refs.isEmpty()) {
                continue;
            }
            String status = AckHelper.extractStatus(fullPdu);
            for (String ref : refs) {
                try {
                    saveDataAck(new AckDto(ref, status));
                    anyUpdated = true;
                    break;
                } catch (IllegalStateException e) {
                    log.info("ACK (PDU) : reference={} sans virement, essai référence suivante", ref);
                }
            }
        }
        // Si aucun bloc Saa:DataPDU valide : traiter tout le fichier comme un seul ACK (ex. XML déplacé en .ack)
        if (!anyUpdated) {
            List<String> refs = AckHelper.extractAllReferencesOrdered(ackContent);
            if (refs == null || refs.isEmpty()) {
                throw new IllegalArgumentException("ACK invalide : référence (BizMsgIdr / MsgId / Reference) introuvable dans le fichier");
            }
            String status = AckHelper.extractStatus(ackContent);
            log.info("ACK : {} référence(s) candidate(s) extraite(s), recherche virement en base", refs.size());
            for (String ref : refs) {
                try {
                    saveDataAck(new AckDto(ref, status));
                    return;
                } catch (IllegalStateException e) {
                    log.info("ACK : reference={} sans virement, essai référence suivante", ref);
                }
            }
            throw new IllegalStateException("ACK invalide : aucune des " + refs.size() + " référence(s) trouvée(s) ne correspond à un VirementEmis en base");
        }
    }

    private AckDto parseMxForData(String contentMx) {
        String reference = AckHelper.extractReference(contentMx);
        String status = AckHelper.extractStatus(contentMx);
        return new AckDto(reference, status);
    }

    /**
     * Met à jour le statut en base : obligatoirement "ACK" ou "NACK".
     * Recherche par référence exacte puis par TRIM si besoin.
     */
    private void saveDataAck(AckDto ackDto) {
        if (ackDto == null) {
            throw new IllegalArgumentException("ACK invalide : objet nul");
        }
        if (ackDto.getReference() == null || ackDto.getReference().trim().isEmpty()) {
            throw new IllegalArgumentException("ACK invalide : référence (BizMsgIdr / MsgId / Reference) manquante ou vide");
        }

        String ref = truncate(ackDto.getReference().trim(), 35);
        // Statut obligatoire : ACK ou NACK (normalisé si absent ou autre valeur)
        String statutRaw = ackDto.getStatus() != null ? ackDto.getStatus().trim() : "";
        String statut = truncate(AckHelper.normalizeStatutSwift(statutRaw.isEmpty() ? "ACK" : statutRaw), 50);

        List<VirementEmis> virements = virementEmisRepository.findByReferenceTrimmed(ref);
        if (virements.isEmpty()) {
            virements = virementEmisRepository.findByReference(ref);
        }
        if (virements.isEmpty()) {
            log.warn("ACK : reference={} aucun VirementEmis trouvé (vérifier que la référence du .ack = GrpHdr/MsgId du .xml)", ref);
            throw new IllegalStateException("ACK invalide : reference=" + ref + " aucun VirementEmis correspondant trouvé");
        }
        for (VirementEmis v : virements) {
            // On ne modifie pas les champs de la clé primaire (id_statut, id_sop, etc.)
            // pour éviter l'erreur Hibernate "identifier ... was altered".
            v.setStatut(statut);
            virementEmisRepository.save(v);
        }
        log.info("ACK appliqué : reference={}, statut={}, {} virement(s) mis à jour en base", ref, statut, virements.size());
    }
}

