package com.stage.swift.web.dto;

public class LoginResponse {

    private final String token;
    private final String email;
    private final String roleCode;
    private final String nom;
    private final String prenom;

    public LoginResponse(String token, String email, String roleCode, String nom, String prenom) {
        this.token = token;
        this.email = email;
        this.roleCode = roleCode;
        this.nom = nom != null ? nom : "";
        this.prenom = prenom != null ? prenom : "";
    }

    public String getToken() {
        return token;
    }

    public String getEmail() {
        return email;
    }

    public String getRoleCode() {
        return roleCode;
    }

    public String getNom() {
        return nom;
    }

    public String getPrenom() {
        return prenom;
    }
}

