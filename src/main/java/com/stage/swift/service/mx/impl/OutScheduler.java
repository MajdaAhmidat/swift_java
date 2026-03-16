package com.stage.swift.service.mx.impl;

import com.stage.swift.service.mx.OutDocumentProcessorService;
import com.stage.swift.utils.PropertiesLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.List;

/**
 * Scheduler flux reçu (OUT) : OUT_SAA (.ack) puis OUT_SOP (.xml).
 * - OUT_SAA : crée VirementRecu + MessageRecu → SAVE_SAA / ERREUR_SAA
 * - OUT_SOP : met à jour statut VirementRecu → SAVE_SOP / ERREUR_SOP
 */
@Component
public class OutScheduler {

    private static final Logger log = LoggerFactory.getLogger(OutScheduler.class);

    private final OutDocumentProcessorService outDocumentProcessorService;

    public OutScheduler(OutDocumentProcessorService outDocumentProcessorService) {
        this.outDocumentProcessorService = outDocumentProcessorService;
    }

    @Scheduled(initialDelayString = "${batch.poll.initial.delay.ms:3000}", fixedDelayString = "${batch.poll.delay.ms:10000}")
    public void pollOutDirectories() {
        String baseDir = getBaseDirectory();
        log.info("[OutScheduler] Polling OUT (base={})", baseDir);

        List<String> outSaaDirs = PropertiesLoader.getDirectories("output.saa.directory");
        if (outSaaDirs == null || outSaaDirs.isEmpty()) {
            log.warn("[OutScheduler] Aucun dossier OUT_SAA configuré (output.saa.directory)");
        } else {
            for (String dirName : outSaaDirs) {
                String path = new File(dirName).isAbsolute() ? dirName : new File(baseDir, dirName).getAbsolutePath();
                log.info("[OutScheduler] Traitement OUT_SAA .ack dans: {}", path);
                outDocumentProcessorService.processOutSaa(path);
            }
        }

        List<String> outSopDirs = PropertiesLoader.getDirectories("output.sop.directory");
        if (outSopDirs == null || outSopDirs.isEmpty()) {
            log.warn("[OutScheduler] Aucun dossier OUT_SOP configuré (output.sop.directory)");
        } else {
            for (String dirName : outSopDirs) {
                String path = new File(dirName).isAbsolute() ? dirName : new File(baseDir, dirName).getAbsolutePath();
                log.info("[OutScheduler] Traitement OUT_SOP .xml dans: {}", path);
                outDocumentProcessorService.processOutSop(path);
            }
        }
    }

    private String getBaseDirectory() {
        String base = PropertiesLoader.getProperty("swift.batch.basedir");
        if (base != null && !base.trim().isEmpty()) {
            return new File(base.trim()).getAbsolutePath();
        }
        String userDir = new File(System.getProperty("user.dir")).getAbsolutePath();
        if (new File(userDir, "IN_SOP").exists() || new File(userDir, "OUT_SAA").exists()) {
            return userDir;
        }
        File swiftFin = new File(userDir, "swift_fin");
        if (new File(swiftFin, "IN_SOP").exists() || new File(swiftFin, "OUT_SAA").exists()) {
            log.info("[OutScheduler] OUT trouvé sous user.dir/swift_fin (lancement depuis dossier père)");
            return swiftFin.getAbsolutePath();
        }
        return userDir;
    }
}
