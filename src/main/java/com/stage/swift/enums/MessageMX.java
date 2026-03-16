package com.stage.swift.enums;

/**
 * Types de messages MX supportés.
 * pacs.008 / pacs.009 : instructions de virement ; camt.054 : notification débit/crédit .
 */
public enum MessageMX {

    PACS008("pacs.008.001.08"),
    PACS009("pacs.009.001.08"),
    CAMT054("camt.054.001.08");

    private static final String PREFIX_008 = "pacs.008";
    private static final String PREFIX_009 = "pacs.009";
    private static final String PREFIX_054 = "camt.054";

    private final String label;

    MessageMX(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

    /**
     * Détecte le type MX à partir du contenu XML.
     */
    public static MessageMX fromXmlContent(String xml) {
        if (xml == null) return null;
        if (xml.contains(PREFIX_009)) return PACS009;
        if (xml.contains(PREFIX_008)) return PACS008;
        if (xml.contains(PREFIX_054)) return CAMT054;
        return null;
    }
}
