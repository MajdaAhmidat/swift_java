package com.stage.swift.service.mx;

/**
 * Traitement des fichiers .ack en flux reçu (OUT_SAA).
 * Crée VirementRecu + MessageRecu avec tous les champs remplis
 */
public interface AckRecuService {

    void processAckFileRecu(String ackContent);

    void processAckFileRecu(String ackContent, String sourceFilePath);
}
