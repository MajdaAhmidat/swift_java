package com.stage.swift.helpers;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Extraction des données depuis un message .ack (SAA) : référence du message et statut réseau.
 * Obligatoire : statut normalisé en "ACK" ou "NACK".
 */
public final class AckHelper {

    /** GrpHdr/MsgId = référence stockée en base pour PACS 008/009 (à prioriser pour XML). */
    private static final Pattern REFERENCE_GRP_MSG_ID = Pattern.compile(
            "<[^:>]*:?GrpHdr[^>]*>(?s).*?<[^:>]*:?MsgId[^>]*>([^<]+)</[^:>]*:?MsgId>",
            Pattern.CASE_INSENSITIVE);
    /** BizMsgIdr ou équivalent (MsgId / Reference) - plusieurs variantes. */
    private static final Pattern REFERENCE = Pattern.compile(
            "<[^:>]*:?(?:BizMsgIdr|Reference|MsgId|OriginalMessageId|MessageId)[^>]*>([^<]+)</[^:>]*:?(?:BizMsgIdr|Reference|MsgId|OriginalMessageId|MessageId)>",
            Pattern.CASE_INSENSITIVE);
    private static final Pattern REFERENCE_SIMPLE = Pattern.compile(
            "<(?:Reference|MsgId|MessageId|BizMsgIdr)>([^<]+)</(?:Reference|MsgId|MessageId|BizMsgIdr)>",
            Pattern.CASE_INSENSITIVE);
    /** Contenu de balise MsgId/Reference/MessageId quand la valeur est 32 hex (stocké en base). */
    private static final Pattern REFERENCE_TAG_32HEX = Pattern.compile(
            "<[^:>]*:?(?:MsgId|MessageId|Reference|BizMsgIdr)[^>]*>\\s*([a-fA-F0-9]{32})\\s*</[^>]*>",
            Pattern.CASE_INSENSITIVE);
    /** Contenu de type ID (32 hex) entre > et < ; espaces/newlines autorisés. */
    private static final Pattern REFERENCE_HEX_ID = Pattern.compile(">\\s*([a-fA-F0-9]{32})\\s*<");

    /**
     * Statut réseau renvoyé par SAA dans les ACK/SOP :

     */
    private static final Pattern STATUS = Pattern.compile(
            "<\\s*Saa:NetworkDeliveryStatus[^>]*>([^<]+)</\\s*Saa:NetworkDeliveryStatus\\s*>",
            Pattern.CASE_INSENSITIVE);

    private AckHelper() {
    }

    /**
     * Extrait la référence du message (celle stockée en virement_emis.reference).
     * Pour XML PACS/CAMT on priorise GrpHdr/MsgId ; sinon BizMsgIdr, MsgId, Reference...
     */
    public static String extractReference(String ackContent) {
        List<String> all = extractAllReferencesOrdered(ackContent);
        return all.isEmpty() ? null : all.get(0);
    }

    /**
     * Extrait toutes les références candidates (GrpHdr/MsgId en premier, puis toutes les autres).
     * Permet d'essayer chaque valeur jusqu'à trouver le virement en base (ordre préservé, sans doublon).
     */
    public static List<String> extractAllReferencesOrdered(String ackContent) {
        Set<String> seen = new LinkedHashSet<>();
        List<String> ordered = new ArrayList<>();
        if (ackContent == null) return ordered;
        // 1) Tous les GrpHdr/MsgId (souvent la référence stockée en base pour PACS 008/009)
        if (ackContent.toUpperCase().contains("GRPHDR")) {
            Matcher m = REFERENCE_GRP_MSG_ID.matcher(ackContent);
            while (m.find()) {
                String v = m.group(1).trim();
                if (!v.isEmpty() && seen.add(v)) ordered.add(v);
            }
        }
        // 2) Toutes les autres balises Reference/MsgId/BizMsgIdr/...
        Matcher m = REFERENCE.matcher(ackContent);
        while (m.find()) {
            String v = m.group(1).trim();
            if (!v.isEmpty() && seen.add(v)) ordered.add(v);
        }
        m = REFERENCE_SIMPLE.matcher(ackContent);
        while (m.find()) {
            String v = m.group(1).trim();
            if (!v.isEmpty() && seen.add(v)) ordered.add(v);
        }
        // 3) Contenu 32 hex dans balises MsgId/Reference/MessageId (souvent stocké en base)
        m = REFERENCE_TAG_32HEX.matcher(ackContent);
        while (m.find()) {
            String v = m.group(1).trim();
            if (!v.isEmpty() && seen.add(v)) ordered.add(v);
        }
        // 4) Ids 32 hex entre > et < (fallback)
        m = REFERENCE_HEX_ID.matcher(ackContent);
        while (m.find()) {
            String v = m.group(1).trim();
            if (!v.isEmpty() && seen.add(v)) ordered.add(v);
        }
        return ordered;
    }

    /** Extrait le statut réseau (ACK, NACK, Accepted, Rejected...) depuis le contenu .ack. */
    public static String extractStatus(String ackContent) {
        if (ackContent == null) return null;
        Matcher m = STATUS.matcher(ackContent);
        return m.find() ? m.group(1).trim() : null;
    }

    /** Normalise le statut en "ACK" ou "NACK" (obligatoire pour statut_swift). */
    public static String normalizeStatutSwift(String status) {
        if (status == null || status.trim().isEmpty()) return "ACK";
        String s = status.trim().toUpperCase();
        if (s.contains("NACK") || s.contains("REJECT") || s.contains("FAIL") || s.contains("ERROR") || s.contains("REFUS")) return "NACK";
        return "ACK";
    }
}
