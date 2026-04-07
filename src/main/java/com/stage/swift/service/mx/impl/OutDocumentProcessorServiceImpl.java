package com.stage.swift.service.mx.impl;

import com.stage.swift.service.mx.AckRecuService;
import com.stage.swift.service.mx.OutDocumentProcessorService;
import com.stage.swift.service.mx.SopRecuService;
import com.stage.swift.utils.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;

/**
 * Implémentation du traitement OUT (reçu) : OUT_SAA (.ack) puis OUT_SOP (.xml).
 */
@Service
public class OutDocumentProcessorServiceImpl implements OutDocumentProcessorService {

    private static final Logger log = LoggerFactory.getLogger(OutDocumentProcessorServiceImpl.class);

    private final AckRecuService ackRecuService;
    private final SopRecuService sopRecuService;

    public OutDocumentProcessorServiceImpl(AckRecuService ackRecuService, SopRecuService sopRecuService) {
        this.ackRecuService = ackRecuService;
        this.sopRecuService = sopRecuService;
    }

    @Override
    public void processOutSaa(String directoryPath) {
        File directory = new File(directoryPath);
        if (!directory.exists() || !directory.isDirectory()) {
            log.warn("[OutDocumentProcessor] Répertoire OUT_SAA inexistant ou invalide : {}", directoryPath);
            return;
        }
        File[] files = directory.listFiles((dir, name) -> name != null && name.toLowerCase().endsWith(".ack"));
        if (files == null) return;
        int count = 0;
        for (File file : files) {
            if (file == null || !file.exists() || !file.isFile()) {
                continue;
            }
            String path = file.getPath();
            try {
                String content = FileUtils.fileToString(file);
                String targetSavePath = FileUtils.resolveSaveTargetPath(path).toString();
                ackRecuService.processAckFileRecu(content, targetSavePath);
                FileUtils.moveToSave(path);
                count++;
                log.info("[OutDocumentProcessor] Fichier .ack recu traité → VirementRecu + MessageRecu créés → SAVE_SAA : {}", file.getName());
            } catch (Exception e) {
                if (isIgnorableOutSaaError(e)) {
                    log.warn("[OutDocumentProcessor] Fichier .ack recu ignoré (cas fonctionnel) → SAVE_SAA : {} : {}", path, e.getMessage());
                    try {
                        FileUtils.moveToSave(path);
                    } catch (IOException io) {
                        log.error("[OutDocumentProcessor] Impossible de déplacer vers SAVE_SAA : {}", io.getMessage());
                    }
                    continue;
                }
                log.error("[OutDocumentProcessor] Erreur fichier .ack recu → ERREUR_SAA : {} : {}", path, e.getMessage());
                try {
                    FileUtils.moveToRejet(path);
                } catch (IOException io) {
                    log.error("[OutDocumentProcessor] Impossible de déplacer vers ERREUR_SAA : {}", io.getMessage());
                }
            }
        }
        if (count > 0) {
            log.info("[OutDocumentProcessor] {} fichier(s) .ack traités dans {}", count, directoryPath);
        }
    }

    @Override
    public void processOutSop(String directoryPath) {
        File directory = new File(directoryPath);
        if (!directory.exists() || !directory.isDirectory()) {
            log.warn("[OutDocumentProcessor] Répertoire OUT_SOP inexistant ou invalide : {}", directoryPath);
            return;
        }
        File[] files = directory.listFiles((dir, name) -> name != null && name.toLowerCase().endsWith(".xml"));
        if (files == null) return;
        int count = 0;
        for (File file : files) {
            if (file == null || !file.exists() || !file.isFile()) {
                continue;
            }
            String path = file.getPath();
            try {
                String content = FileUtils.fileToString(file);
                sopRecuService.processSopRecu(content);
                FileUtils.moveToSave(path);
                count++;
                log.info("[OutDocumentProcessor] Fichier .xml SOP recu traité → statut VirementRecu mis à jour → SAVE_SOP : {}", file.getName());
            } catch (Exception e) {
                if (isIgnorableOutSopError(e)) {
                    log.warn("[OutDocumentProcessor] Fichier .xml SOP recu ignoré (aucune correspondance) → SAVE_SOP : {} : {}", path, e.getMessage());
                    try {
                        FileUtils.moveToSave(path);
                    } catch (IOException io) {
                        log.error("[OutDocumentProcessor] Impossible de déplacer vers SAVE_SOP : {}", io.getMessage());
                    }
                    continue;
                }
                log.error("[OutDocumentProcessor] Erreur fichier .xml SOP recu → ERREUR_SOP : {} : {}", path, e.getMessage());
                try {
                    FileUtils.moveToRejet(path);
                } catch (IOException io) {
                    log.error("[OutDocumentProcessor] Impossible de déplacer vers ERREUR_SOP : {}", io.getMessage());
                }
            }
        }
        if (count > 0) {
            log.info("[OutDocumentProcessor] {} fichier(s) .xml SOP traités dans {}", count, directoryPath);
        }
    }

    private boolean isIgnorableOutSaaError(Exception e) {
        if (e == null || e.getMessage() == null) {
            return false;
        }
        String msg = e.getMessage().toLowerCase();
        return msg.contains("type de message mx non supporté dans .ack recu");
    }

    private boolean isIgnorableOutSopError(Exception e) {
        if (e == null || e.getMessage() == null) {
            return false;
        }
        String msg = e.getMessage().toLowerCase();
        return msg.contains("aucune référence trouvée ne correspond à un virementrecu en base");
    }
}
