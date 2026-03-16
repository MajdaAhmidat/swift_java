package com.stage.swift.service.mx;

/**
 * Traitement des fichiers .ack en flux reçu (OUT_SAA).
 * Crée VirementRecu + MessageRecu avec tous les champs remplis sauf le statut (mis à jour par SOP).
 */
public interface AckRecuService {

    void processAckFileRecu(String ackContent);
}
