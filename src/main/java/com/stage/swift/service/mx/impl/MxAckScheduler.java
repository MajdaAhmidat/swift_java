package com.stage.swift.service.mx.impl;

import com.prowidesoftware.swift.model.MessageStandardType;
import com.stage.swift.service.mx.DocumentProcessorService;
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
public class MxAckScheduler {

    private static final Logger log = LoggerFactory.getLogger(MxAckScheduler.class);

    private final DocumentProcessorService documentProcessorService;
    private final AtomicBoolean running = new AtomicBoolean(false);

    public MxAckScheduler(DocumentProcessorService documentProcessorService) {
        this.documentProcessorService = documentProcessorService;
    }

    /**
     * Polling périodique : premier passage après 3 s, puis toutes les 10 s.
     */
    @Scheduled(initialDelayString = "${batch.poll.initial.delay.ms:3000}", fixedDelayString = "${batch.poll.delay.ms:10000}")
    public void pollInputDirectories() {
        if (!running.compareAndSet(false, true)) {
            log.warn("[MxAckScheduler] Polling ignoré: un traitement est déjà en cours");
            return;
        }
        try {
        String baseDir = getBaseDirectory();
        log.info("[MxAckScheduler] Polling des dossiers (base={})", baseDir);

        // 1) SOP (.xml) - dossiers configurés dans application.properties (input.mx.directory)
        List<String> mxDirs = PropertiesLoader.getDirectories("input.mx.directory");
        if (mxDirs != null && !mxDirs.isEmpty()) {
            mxDirs = toAbsolutePaths(mxDirs, baseDir);
            mxDirs = keepExistingDirectories(mxDirs);
        }
        if (mxDirs == null || mxDirs.isEmpty()) {
            log.warn("[MxAckScheduler] Aucun dossier IN_SOP configuré/trouvé (input.mx.directory).");
            return;
        }
        log.info("[MxAckScheduler] Traitement SOP .xml dans: {}", mxDirs);
        documentProcessorService.processDocuments(mxDirs, MessageStandardType.MX);

        // 2) SAA (.ack) - IN_SAA : traiter les .ack, mettre à jour statut_swift, puis FILESAVE / ERRORLOGS
        List<String> ackDirs = PropertiesLoader.getDirectories("input.ack.directory");
        if (ackDirs != null && !ackDirs.isEmpty()) {
            ackDirs = toAbsolutePaths(ackDirs, baseDir);
            log.info("[MxAckScheduler] Traitement SAA .ack dans: {}", ackDirs);
            documentProcessorService.processDocuments(ackDirs, MessageStandardType.MX);
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
            log.info("[MxAckScheduler] Dossiers trouvés sous user.dir/swift_fin");
            return swiftFin.getAbsolutePath();
        }
        return userDir;
    }

    private static List<String> toAbsolutePaths(List<String> dirs, String baseDir) {
        List<String> out = new ArrayList<>(dirs.size());
        for (String d : dirs) {
            File f = new File(d);
            out.add(f.isAbsolute() ? d : new File(baseDir, d).getAbsolutePath());
        }
        return out;
    }

    private static List<String> keepExistingDirectories(List<String> dirs) {
        List<String> out = new ArrayList<>();
        for (String d : dirs) {
            File f = new File(d);
            if (f.exists() && f.isDirectory()) {
                out.add(f.getAbsolutePath());
            }
        }
        return out;
    }

}

