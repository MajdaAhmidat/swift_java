package com.stage.swift.utils;

/**
 * Utilitaires communs pour les chaînes et formats.
 * <p>
 * <b>Rôle :</b> Méthodes statiques réutilisables (validation, troncature, jointure)
 * sans dépendance métier. Utilisées par les DTOs, services et helpers.
 * </p>
 */
public final class Commons {

    private Commons() {}

    /**
     * Indique si la chaîne est non nulle et non vide.
     * Utilisé pour valider des champs obligatoires (référence, BIC, etc.).
     */
    public static boolean stringNotEmpty(String str) {
        return str != null && !str.isEmpty();
    }

    /**
     * Tronque un code/texte à une longueur max (ex. référence 35 car).
     * Retourne null si code est null.
     */
    public static String trimToLength(String code, int maxLength) {
        if (code == null) return null;
        return code.length() > maxLength ? code.substring(0, maxLength) : code;
    }

    /**
     * Joint des chaînes non vides avec un délimiteur. Les parties null ou blanches sont ignorées.
     */
    public static String joinNonEmpty(String delimiter, String... parts) {
        if (parts == null) return "";
        StringBuilder sb = new StringBuilder();
        for (String p : parts) {
            if (p != null && !p.trim().isEmpty()) {
                if (sb.length() > 0) sb.append(delimiter);
                sb.append(p);
            }
        }
        return sb.toString();
    }
}
