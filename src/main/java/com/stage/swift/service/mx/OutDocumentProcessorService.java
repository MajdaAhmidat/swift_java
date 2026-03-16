package com.stage.swift.service.mx;

/**
 * Traitement des dossiers OUT (flux reçu) :
 * - OUT_SAA : fichiers .ack → crée VirementRecu + MessageRecu → SAVE_SAA ou ERREUR_SAA
 * - OUT_SOP : fichiers .xml → met à jour statut VirementRecu → SAVE_SOP ou ERREUR_SOP
 */
public interface OutDocumentProcessorService {

    /**
     * Traite le dossier OUT_SAA : tous les .ack, création VirementRecu + MessageRecu, déplacement SAVE_SAA / ERREUR_SAA.
     */
    void processOutSaa(String directoryPath);

    /**
     * Traite le dossier OUT_SOP : tous les .xml, mise à jour statut VirementRecu par référence, déplacement SAVE_SOP / ERREUR_SOP.
     */
    void processOutSop(String directoryPath);
}
