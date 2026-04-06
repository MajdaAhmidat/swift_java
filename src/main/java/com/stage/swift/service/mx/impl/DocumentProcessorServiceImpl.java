package com.stage.swift.service.mx.impl;

import com.prowidesoftware.swift.model.MessageStandardType;
import com.stage.swift.service.mx.AckService;
import com.stage.swift.service.mx.DocumentProcessorService;
import com.stage.swift.service.mx.MessageProcessorService;
import com.stage.swift.utils.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Implémentation de {@link DocumentProcessorService}.
 */
@Service
public class DocumentProcessorServiceImpl implements DocumentProcessorService {

    private static final Logger logger = LoggerFactory.getLogger(DocumentProcessorServiceImpl.class);

    private final MessageProcessorService messageProcessorService;
    private final AckService ackService;

    public DocumentProcessorServiceImpl(MessageProcessorService messageProcessorService,
                                        AckService ackService) {
        this.messageProcessorService = messageProcessorService;
        this.ackService = ackService;
    }

    @Override
    public void processDocuments(List<String> directoriesPath, MessageStandardType messageType) {
        for (String directoryPath : directoriesPath) {
            logger.info("[DocumentProcessorService] Début traitement {} de type {}", directoryPath, messageType.name());
            File directory = new File(directoryPath);
            if (directory.exists() && directory.isDirectory()) {
                File[] allFiles = directory.listFiles((dir, name) -> name != null);
                if (allFiles != null) {
                    int xmlCount = 0;
                    int ackCount = 0;
                    try {
                        for (File file : allFiles) {
                            if (file == null || !file.exists() || !file.isFile()) {
                                continue;
                            }
                            String name = file.getName();
                            if (name == null) continue;
                            String lower = name.toLowerCase();
                            if (lower.endsWith(".xml")) {
                                xmlCount++;
                                processXmlDocument(file, messageType);
                            } else if (lower.endsWith(".ack")) {
                                ackCount++;
                                processAckDocument(file);
                            }
                            // autres extensions ignorées
                        }
                        logger.info("[DocumentProcessorService] {} fichier(s) .xml et {} fichier(s) .ack traités dans {}", xmlCount, ackCount, directoryPath);
                    } catch (IOException e) {
                        logger.error("[DocumentProcessorService] Erreur IO répertoire {} : {}", directoryPath, e.getMessage());
                    }
                }
            } else {
                logger.warn("[DocumentProcessorService] Répertoire inexistant ou invalide : {}", directoryPath);
            }
            logger.info("[DocumentProcessorService] Fin traitement {}", directoryPath);
        }
    }

    private void processXmlDocument(File file, MessageStandardType messageType) throws IOException {
        String content = FileUtils.fileToString(file);
        String filePath = file.getPath();
        try {
            if (MessageStandardType.MX.equals(messageType)) {
                messageProcessorService.process(content, messageType);
            } else {
                logger.warn("[DocumentProcessorService] Type {} non supporté pour .xml (PACS008/PACS009)", messageType.name());
            }
            // Après traitement, le .xml est archivé en FILESAVE
            FileUtils.moveToSave(filePath);
        } catch (Exception e) {
            logger.error("[DocumentProcessorService] Erreur fichier {} : {}", filePath, e.getMessage());
            FileUtils.moveToRejet(filePath);
        }
    }

    /**
     * Traitement SAA : met à jour statut_swift (ACK/NACK) en base, puis FILESAVE ou ERRORLOGS.
     */
    private void processAckDocument(File file) throws IOException {
        String content = FileUtils.fileToString(file);
        String filePath = file.getPath();
        try {
            ackService.processAckFile(content);
            FileUtils.moveToSave(filePath);
            logger.info("[DocumentProcessorService] Fichier .ack traité → statut_swift mis à jour → déplacé vers FILESAVE : {}", file.getName());
        } catch (Exception e) {
            logger.error("[DocumentProcessorService] Erreur fichier .ack → déplacé vers ERRORLOGS : {} : {}", filePath, e.getMessage());
            FileUtils.moveToRejet(filePath);
        }
    }
}
