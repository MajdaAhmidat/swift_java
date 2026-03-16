package com.stage.swift.dto.entity;

import java.io.Serializable;

/**
 * DTO pour l'entité Bic (référentiel des codes BIC).
 * <p><b>Rôle :</b> Expose en API le code BIC, BIC ordonnateur/bénéficiaire, libellés et code banque.
 * Utilisé par les contrôleurs BIC et par les écrans de saisie de virements.</p>
 */
public class BicDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long codeBic;
    private String bicOrdonnateur;
    private String bicBeneficiare;
    private String libelleBic;
    private String libelleBq;
    private String codeBq;

    public BicDTO() {
    }

    public Long getCodeBic() {
        return codeBic;
    }

    public void setCodeBic(Long codeBic) {
        this.codeBic = codeBic;
    }

    public String getBicOrdonnateur() {
        return bicOrdonnateur;
    }

    public void setBicOrdonnateur(String bicOrdonnateur) {
        this.bicOrdonnateur = bicOrdonnateur;
    }

    public String getBicBeneficiare() {
        return bicBeneficiare;
    }

    public void setBicBeneficiare(String bicBeneficiare) {
        this.bicBeneficiare = bicBeneficiare;
    }

    public String getLibelleBic() {
        return libelleBic;
    }

    public void setLibelleBic(String libelleBic) {
        this.libelleBic = libelleBic;
    }

    public String getLibelleBq() {
        return libelleBq;
    }

    public void setLibelleBq(String libelleBq) {
        this.libelleBq = libelleBq;
    }

    public String getCodeBq() {
        return codeBq;
    }

    public void setCodeBq(String codeBq) {
        this.codeBq = codeBq;
    }
}
