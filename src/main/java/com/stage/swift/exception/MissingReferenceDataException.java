package com.stage.swift.exception;

/**
 * Levée lorsque les données de référence (statut, adresse, sop, bic, type_message)
 * requises pour enregistrer un virement reçu sont absentes en base.
 */
public class MissingReferenceDataException extends RuntimeException {

    public MissingReferenceDataException(String message) {
        super(message);
    }
}
