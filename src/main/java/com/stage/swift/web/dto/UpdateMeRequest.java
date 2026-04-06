package com.stage.swift.web.dto;

/**
 * Corps optionnel pour PATCH /api/auth/me : mot de passe et/ou profil (nom, prénom).
 */
public class UpdateMeRequest {
    private String currentPassword;
    private String newPassword;
    private String nom;
    private String prenom;

    public String getCurrentPassword() {
        return currentPassword;
    }

    public void setCurrentPassword(String currentPassword) {
        this.currentPassword = currentPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
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
}
