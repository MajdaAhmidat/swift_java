package com.stage.swift.service.mx;

import com.prowidesoftware.swift.model.MessageStandardType;

import java.util.List;

/**
 * Service de traitement batch par dossiers.
 *
 * Flux souhaité :
 * - IN_SOP : consomme les fichiers .xml, traite et enregistre en base, puis archive en FILESAVE ou ERRORLOGS
 * - IN_SAA : consomme les fichiers .ack, met à jour statut_swift (ACK/NACK) en base, puis archive en FILESAVE ou ERRORLOGS
 *
 * Aucun renommage .xml → .ack : chaque dossier consomme directement son propre type de fichiers.
 */
public interface DocumentProcessorService {

    /**
     * Traite les dossiers donnés pour le type de message indiqué.
     * - .xml → traitement SOP (PACS/CAMT) via {@link MessageProcessorService}
     * - .ack → traitement SAA via {@link com.stage.swift.service.mx.AckService}
     */
    void processDocuments(List<String> directoriesPath, MessageStandardType messageType);
}
