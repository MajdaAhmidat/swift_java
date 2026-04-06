package com.stage.swift.entity;

import javax.persistence.*;

@Entity
@Table(schema = "swift", name = "permissions")
public class Permission {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_permission")
    private Integer idPermission;

    @Column(name = "module", nullable = false, length = 100)
    private String module;

    @Column(name = "lire", nullable = false)
    private Boolean lire = Boolean.FALSE;

    @Column(name = "creer", nullable = false)
    private Boolean creer = Boolean.FALSE;

    @Column(name = "modifier", nullable = false)
    private Boolean modifier = Boolean.FALSE;

    @Column(name = "supprimer", nullable = false)
    private Boolean supprimer = Boolean.FALSE;

    @Column(name = "valider", nullable = false)
    private Boolean valider = Boolean.FALSE;

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
