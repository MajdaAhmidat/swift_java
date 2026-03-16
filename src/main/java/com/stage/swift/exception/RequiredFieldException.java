package com.stage.swift.exception;

/**
 * Levée lorsqu'un champ obligatoire est manquant ou vide (ex. référence, BIC, montant).
 * <p>
 * <b>Rôle :</b> Permet de signaler une erreur de validation métier (donnée requise absente)
 * distincte de {@link MissingReferenceDataException} (données de référence en base manquantes).
 * </p>
 */
public class RequiredFieldException extends RuntimeException {

    public RequiredFieldException(String fieldName) {
        super("Champ obligatoire manquant ou vide : " + fieldName);
    }

    public RequiredFieldException(String fieldName, Throwable cause) {
        super("Champ obligatoire manquant ou vide : " + fieldName, cause);
    }
}
