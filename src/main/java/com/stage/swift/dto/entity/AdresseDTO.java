package com.stage.swift.dto.entity;

import java.io.Serializable;

/**
 * DTO pour l'entité Adresse (référentiel des adresses).
 * <p><b>Rôle :</b> Expose en API les champs d'adresse (lignes, ville, code postal, pays).
 * Utilisé pour le CRUD adresses et la liaison avec les virements émis/reçus.</p>
 */
public class AdresseDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long idAdresse;
    private String ligne1;
    private String ligne2;
    private String ville;
    private String codePostal;
    private String pays;

    public AdresseDTO() {
    }

    public Long getIdAdresse() {
        return idAdresse;
    }

    public void setIdAdresse(Long idAdresse) {
        this.idAdresse = idAdresse;
    }

    public String getLigne1() {
        return ligne1;
    }

    public void setLigne1(String ligne1) {
        this.ligne1 = ligne1;
    }

    public String getLigne2() {
        return ligne2;
    }

    public void setLigne2(String ligne2) {
        this.ligne2 = ligne2;
    }

    public String getVille() {
        return ville;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }

    public String getCodePostal() {
        return codePostal;
    }

    public void setCodePostal(String codePostal) {
        this.codePostal = codePostal;
    }

    public String getPays() {
        return pays;
    }

    public void setPays(String pays) {
        this.pays = pays;
    }
}
