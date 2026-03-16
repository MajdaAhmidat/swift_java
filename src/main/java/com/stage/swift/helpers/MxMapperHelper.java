package com.stage.swift.helpers;

import com.stage.swift.entity.VirementRecu;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

/**
 * Helper commun pour le mapping MX → VirementRecu (PACS008 / PACS009),
 */
public final class MxMapperHelper {

    private MxMapperHelper() {
    }

    public static String truncate(String s, int max) {
        if (s == null) return "";
        return s.length() <= max ? s : s.substring(0, max);
    }

    public static LocalDate parseLocalDate(Object dateObj) {
        LocalDate def = LocalDate.now();
        if (dateObj == null) return def;
        try {
            String s = dateObj.toString();
            if (s != null && s.length() >= 10) {
                return LocalDate.parse(s.substring(0, 10));
            }
            return LocalDate.parse(s);
        } catch (Exception e) {
            return def;
        }
    }

    public static void setMontantAndDevise(VirementRecu v, BigDecimal amount, String ccy) {
        v.setMontant(amount != null ? amount : BigDecimal.ZERO);
        v.setCodeDevise((ccy == null || ccy.isEmpty()) ? "MAD" : ccy);
    }
}
