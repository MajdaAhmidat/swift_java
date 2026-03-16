package com.stage.swift.utils;

/**
 * Constantes partagées pour le traitement des fichiers (batch).
 */
public final class Constantes {

    private Constantes() {
    }

    public static final String LINE_SEPARATOR = System.getProperty("line.separator");
    public static final String LINE_SEPAR = "/";

    /** Dossiers batch SOP (messages .xml) */
    public static final String IN_SOP_DIR = "IN_SOP";
    public static final String SAVE_SOP_DIR = "SAVE_SOP";
    public static final String ERROR_SOP_DIR = "ERREUR_SOP";

    /** Dossiers batch SAA (messages .ack) */
    public static final String IN_SAA_DIR = "IN_SAA";
    public static final String SAVE_SAA_DIR = "SAVE_SAA";
    public static final String ERROR_SAA_DIR = "ERREUR_SAA";

    /** Dossiers batch OUT (reçu) : SAA en premier, puis SOP met à jour le statut */
    public static final String OUT_SAA_DIR = "OUT_SAA";
    public static final String OUT_SOP_DIR = "OUT_SOP";

    public static final String EXTENSION_XML = ".xml";

    /** Dossier IN par défaut au démarrage (SOP) */
    public static final String DEFAULT_IN_DIR = IN_SOP_DIR;
}
