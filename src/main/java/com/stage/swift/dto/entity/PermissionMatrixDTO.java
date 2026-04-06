package com.stage.swift.dto.entity;

import java.io.Serializable;

public class PermissionMatrixDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer idPermission;
    private String module;
    private Boolean lire;
    private Boolean creer;
    private Boolean modifier;
    private Boolean supprimer;
    private Boolean valider;

    public Integer getIdPermission() {
        return idPermission;
    }

    public void setIdPermission(Integer idPermission) {
        this.idPermission = idPermission;
    }

    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }

    public Boolean getLire() {
        return lire;
    }

    public void setLire(Boolean lire) {
        this.lire = lire;
    }

    public Boolean getCreer() {
        return creer;
    }

    public void setCreer(Boolean creer) {
        this.creer = creer;
    }

    public Boolean getModifier() {
        return modifier;
    }

    public void setModifier(Boolean modifier) {
        this.modifier = modifier;
    }

    public Boolean getSupprimer() {
        return supprimer;
    }

    public void setSupprimer(Boolean supprimer) {
        this.supprimer = supprimer;
    }

    public Boolean getValider() {
        return valider;
    }

    public void setValider(Boolean valider) {
        this.valider = valider;
    }
}
