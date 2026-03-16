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
import java.util.Collections;
import java.util.List;

/**
 * Scheduler qui surveille en continu deux dossiers distincts :
 * - IN_SOP : fichiers SOP (.xml) à traiter et enregistrer en base
 * - IN_SAA : fichiers SAA (.ack) à traiter pour mettre à jour statut_swift
 *
 * Base des chemins : user.dir, ou user.dir/swift_fin si l'IDE lance depuis le dossier père
 * (ex. monitoring) car alors user.dir = dossier père et les dossiers sont dans swift_fin.
 */
@Component
public class MxAckScheduler {

    private static final Logger log = LoggerFactory.getLogger(MxAckScheduler.class);

    private final DocumentProcessorService documentProcessorService;

    public MxAckScheduler(DocumentProcessorService documentProcessorService) {
        this.documentProcessorService = documentProcessorService;
    }

    /**
     * Polling périodique : premier passage après 3 s, puis toutes les 10 s.
     */
    @Scheduled(initialDelayString = "${batch.poll.initial.delay.ms:3000}", fixedDelayString = "${batch.poll.delay.ms:10000}")
    public void pollInputDirectories() {
        String baseDir = getBaseDirectory();
        log.info("[MxAckScheduler] Polling des dossiers (base={})", baseDir);

        // 1) SOP (.xml) - IN_SOP : traiter et enregistrer en base, puis FILESAVE / ERRORLOGS
        List<String> mxDirs = PropertiesLoader.getDirectories("input.mx.directory");
        if (mxDirs == null || mxDirs.isEmpty()) {
            String pathIn = PropertiesLoader.getProperty("swift.app.path.in");
            String dirName = (pathIn != null && !pathIn.trim().isEmpty()) ? pathIn.trim() : "IN_SOP";
            mxDirs = Collections.singletonList(new File(baseDir, dirName).getAbsolutePath());
        } else {
            mxDirs = toAbsolutePaths(mxDirs, baseDir);
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
    }

    private String getBaseDirectory() {
        String base = PropertiesLoader.getProperty("swift.batch.basedir");
        if (base != null && !base.trim().isEmpty()) {
            return new File(base.trim()).getAbsolutePath();
        }
        String userDir = new File(System.getProperty("user.dir")).getAbsolutePath();
        if (new File(userDir, "IN").exists()) {
            return userDir;
        }
        // IDE lance depuis le dossier père (ex. monitoring) → user.dir = père, IN est dans swift_fin
        File swiftFin = new File(userDir, "swift_fin");
        if (new File(swiftFin, "IN").exists()) {
            log.info("[MxAckScheduler] IN trouvé sous user.dir/swift_fin (lancement depuis dossier père)");
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
}

