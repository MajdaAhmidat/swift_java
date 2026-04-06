package com.stage.swift.entity;

import javax.persistence.*;

@Entity
@Table(schema = "swift", name = "utilisateur_permission")
public class UtilisateurPermission {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "id_user", nullable = false)
    private Integer idUser;

    @Column(name = "module", nullable = false, length = 100)
    private String module;

    @Column(name = "lire", nullable = false)
    private Boolean lire;

    @Column(name = "creer", nullable = false)
    private Boolean creer;

    @Column(name = "modifier", nullable = false)
    private Boolean modifier;

    @Column(name = "supprimer", nullable = false)
    private Boolean supprimer;

    @Column(name = "valider", nullable = false)
    private Boolean valider;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getIdUser() {
        return idUser;
    }

    public void setIdUser(Integer idUser) {
        this.idUser = idUser;
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
