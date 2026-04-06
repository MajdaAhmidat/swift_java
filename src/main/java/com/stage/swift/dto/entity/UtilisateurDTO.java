package com.stage.swift.dto.entity;

import java.io.Serializable;
import java.time.OffsetDateTime;

/**
 * DTO pour l'entité Utilisateur (compte utilisateur). Clé composite : idUser + idRoleRole.
 * <p><b>Rôle :</b> Expose login, mot de passe, rôle, actif, date création. Utilisé pour le CRUD utilisateurs et l'authentification.</p>
 */
public class UtilisateurDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer idUser;
    private Integer idRoleRole;
    private String login;
    private String motdepasse;
    private Integer roleId;
    private Boolean actif;
    private OffsetDateTime createdAt;
    private String roleCode; // libellé du rôle (optionnel)
    private String nom;
    private String prenom;
    private String telephone;
    private String departement;
    private String poste;
    private String statut;

    public UtilisateurDTO() {
    }

    public Integer getIdUser() {
        return idUser;
    }

    public void setIdUser(Integer idUser) {
        this.idUser = idUser;
    }

    public Integer getIdRoleRole() {
        return idRoleRole;
    }

    public void setIdRoleRole(Integer idRoleRole) {
        this.idRoleRole = idRoleRole;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getMotdepasse() {
        return motdepasse;
    }

    public void setMotdepasse(String motdepasse) {
        this.motdepasse = motdepasse;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public Boolean getActif() {
        return actif;
    }

    public void setActif(Boolean actif) {
        this.actif = actif;
    }

    public OffsetDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(OffsetDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public String getRoleCode() {
        return roleCode;
    }

    public void setRoleCode(String roleCode) {
        this.roleCode = roleCode;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getDepartement() {
        return departement;
    }

    public void setDepartement(String departement) {
        this.departement = departement;
    }

    public String getPoste() {
        return poste;
    }

    public void setPoste(String poste) {
        this.poste = poste;
    }

    public String getStatut() {
        return statut;
    }

    public void setStatut(String statut) {
        this.statut = statut;
    }
}
