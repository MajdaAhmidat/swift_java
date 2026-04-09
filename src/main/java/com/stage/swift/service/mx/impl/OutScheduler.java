package com.stage.swift.service.mx.impl;

import com.stage.swift.service.mx.OutDocumentProcessorService;
import com.stage.swift.utils.PropertiesLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;


@Component
public class OutScheduler {

    private static final Logger log = LoggerFactory.getLogger(OutScheduler.class);

    private final OutDocumentProcessorService outDocumentProcessorService;
    private final AtomicBoolean running = new AtomicBoolean(false);

    public OutScheduler(OutDocumentProcessorService outDocumentProcessorService) {
        this.outDocumentProcessorService = outDocumentProcessorService;
    }

    @Scheduled(initialDelayString = "${batch.poll.initial.delay.ms:3000}", fixedDelayString = "${batch.poll.delay.ms:10000}")
    public void pollOutDirectories() {
        if (!running.compareAndSet(false, true)) {
            log.warn("[OutScheduler] Polling ignoré: un traitement est déjà en cours");
            return;
        }
        try {
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
            List<String> configured = new ArrayList<>();
            for (String dirName : outSopDirs) {
                String path = new File(dirName).isAbsolute() ? dirName : new File(baseDir, dirName).getAbsolutePath();
                File f = new File(path);
                if (f.exists() && f.isDirectory()) {
                    configured.add(path);
                }
            }
            outSopDirs = configured;
        }
        if (outSopDirs != null) {
            for (String path : outSopDirs) {
                if (path == null || path.trim().isEmpty()) {
                    continue;
                }
                log.info("[OutScheduler] Traitement OUT_SOP .xml dans: {}", path);
                outDocumentProcessorService.processOutSop(path);
            }
        }
        } finally {
            running.set(false);
        }
    }

    private String getBaseDirectory() {
        String base = PropertiesLoader.getProperty("swift.batch.basedir");
        if (base != null && !base.trim().isEmpty()) {
            return new File(base.trim()).getAbsolutePath();
        }
        String userDir = new File(System.getProperty("user.dir")).getAbsolutePath();
        if (new File(userDir, "SAA").exists()) {
            return userDir;
        }
        File swiftFin = new File(userDir, "swift_fin");
        if (new File(swiftFin, "SAA").exists()) {
            log.info("[OutScheduler] Dossiers trouvés sous user.dir/swift_fin");
            return swiftFin.getAbsolutePath();
        }
        return userDir;
    }
}
