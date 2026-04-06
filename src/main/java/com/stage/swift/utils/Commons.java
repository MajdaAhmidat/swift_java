package com.stage.swift.utils;

/**

 * Rôle :Méthodes statiques réutilisables (validation, troncature, jointure)
 * sans dépendance métier. Utilisées par les DTOs, services et helpers.
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
