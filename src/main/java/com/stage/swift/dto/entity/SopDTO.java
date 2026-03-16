package com.stage.swift.dto.entity;

import java.io.Serializable;

/**
 * DTO pour l'entité Sop (référentiel des SOP - systèmes de paiement).
 * <p><b>Rôle :</b> Expose id et libellé SOP. Utilisé pour le CRUD sops et la liaison avec les virements.</p>
 */
public class SopDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private String libelleSop;

    public SopDTO() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLibelleSop() {
        return libelleSop;
    }

    public void setLibelleSop(String libelleSop) {
        this.libelleSop = libelleSop;
    }
}
