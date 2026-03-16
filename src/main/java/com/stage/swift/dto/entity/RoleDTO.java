package com.stage.swift.dto.entity;

import java.io.Serializable;

/**
 * DTO pour l'entité Role (rôles utilisateur).
 * <p><b>Rôle :</b> Expose code et libellé du rôle. Utilisé par le CRUD rôles et par UtilisateurDTO (affichage rôle).</p>
 */
public class RoleDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer idRole;
    private String code;
    private String label;

    public RoleDTO() {
    }

    public RoleDTO(Integer idRole, String code, String label) {
        this.idRole = idRole;
        this.code = code;
        this.label = label;
    }

    public Integer getIdRole() {
        return idRole;
    }

    public void setIdRole(Integer idRole) {
        this.idRole = idRole;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }
}
