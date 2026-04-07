package com.stage.swift.service.mx;

import com.stage.swift.entity.VirementRecu;

/**
 * Service pour le parsing et la persistance des messages ISO 20022
 */
public interface Iso20022MessageService {

    /**
     * Détecte le type de message (pacs.008.001.08 ou pacs.009.001.08), parse le XML
     * et enregistre les données en base (VirementRecu + MessageRecu).
     *
     * @param xml contenu XML (enveloppe AppHdr+Document ou Document seul)
     * @return le VirementRecu créé
     */
    VirementRecu parseAndSave(String xml);

    VirementRecu parseAndSave(String xml, String sourceFilePath);

}
