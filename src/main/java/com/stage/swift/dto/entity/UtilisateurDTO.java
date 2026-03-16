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
}
