package com.stage.swift.service.mx;

/**
 * Service dédié au traitement des messages ACK (SAA).
 * - Lit le contenu .ack
 * - Extrait reference + statut
 * - Met à jour statut_swift dans virement_emis.
 */
public interface AckService {

    /**
     * Traite un fichier / contenu .ack complet (peut contenir un ou plusieurs Saa:DataPDU).
     */
    void processAckFile(String ackContent);
}

