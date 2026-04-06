package com.stage.swift.entity;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.time.OffsetDateTime;

@Entity
@Table(schema = "swift", name = "utilisateur")
@IdClass(Utilisateur.UtilisateurPK.class)
public class Utilisateur {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "utilisateur_seq")
    @SequenceGenerator(name = "utilisateur_seq", sequenceName = "swift.utilisateur_id_seq", allocationSize = 1)
    @Column(name = "id_user")
    private Integer idUser;

    @Id
    @Column(name = "id_role")
    private Integer idRoleRole;

    @Column(name = "email", nullable = false, unique = true, length = 150)
    private String login;

    @Column(name = "motdepasse", nullable = false, columnDefinition = "text")
    private String motdepasse;

    @Column(name = "actif", nullable = false)
    private Boolean actif = true;

    @Column(name = "created_at")
    private OffsetDateTime createdAt;

    @Column(name = "nom", length = 100)
    private String nom;

    @Column(name = "prenom", length = 100)
    private String prenom;

    @Column(name = "telephone", length = 50)
    private String telephone;

    @Column(name = "departement", length = 100)
    private String departement;

    @Column(name = "poste", length = 100)
    private String poste;

    @Column(name = "statut", length = 50)
    private String statut;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_role", referencedColumnName = "id_role", insertable = false, updatable = false)
    private Role role;

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

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    /** PK composite  */
    @Getter @Setter @NoArgsConstructor @AllArgsConstructor @EqualsAndHashCode
    public static class UtilisateurPK implements Serializable {
        private static final long serialVersionUID = 1L;
        private Integer idUser;
        private Integer idRoleRole;
    }
}
