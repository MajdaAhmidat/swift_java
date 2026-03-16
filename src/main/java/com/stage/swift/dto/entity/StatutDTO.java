package com.stage.swift.dto.entity;

import java.io.Serializable;

/**
 * DTO pour l'entité Statut (référentiel des statuts de virement).
 * <p><b>Rôle :</b> Expose id, code et libellé du statut. Utilisé pour le CRUD statuts et la liaison avec les virements.</p>
 */
public class StatutDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long idStatut;
    private String codeStatut;
    private String libelleStatut;

    public StatutDTO() {
    }

    public Long getIdStatut() {
        return idStatut;
    }

    public void setIdStatut(Long idStatut) {
        this.idStatut = idStatut;
    }

    public String getCodeStatut() {
        return codeStatut;
    }

    public void setCodeStatut(String codeStatut) {
        this.codeStatut = codeStatut;
    }

    public String getLibelleStatut() {
        return libelleStatut;
    }

    public void setLibelleStatut(String libelleStatut) {
        this.libelleStatut = libelleStatut;
    }
}
