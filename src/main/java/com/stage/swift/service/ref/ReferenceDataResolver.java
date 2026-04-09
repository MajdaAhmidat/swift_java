package com.stage.swift.service.ref;


public interface ReferenceDataResolver {

    /**
     * Résout l'id SOP à partir du code BIC du message (ordonnateur ou bénéficiaire).
     * Si un BIC en base a ce code et un id_sop renseigné, on l'utilise ; sinon fallback.
     */
    long resolveSopIdFromBic(String bicCode);

    /**
     * Résout l'id statut à partir du code statut (ex. EN_ATTENTE, ACK, NACK, EN_ATTENTE_SOP, TRAITE).
     * Si le code existe en base, on retourne son id_statut ; sinon fallback.
     */
    long resolveStatutIdByCode(String codeStatut);

    /**
     * Résout le code_bic (PK table bic) à partir du code BIC du message.
     * Si un BIC en base a ce code, on retourne son code_bic ; sinon fallback.
     */
    long resolveCodeBicFromBic(String bicCode);

    /**
     * Résout/crée le code_bic en forçant l'association au SOP fourni.
     */
    long resolveCodeBicFromBic(String bicCode, Long idSop);
}
