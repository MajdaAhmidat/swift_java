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
    @Column(name = "id_user")
    private Integer idUser;

    @Id
    @Column(name = "id_role_role")
    private Integer idRoleRole;

    @Column(name = "login", nullable = false, unique = true, length = 100)
    private String login;

    @Column(name = "motdepasse", nullable = false, columnDefinition = "text")
    private String motdepasse;

    @Column(name = "role_id", nullable = false)
    private Integer roleId;

    @Column(name = "actif", nullable = false)
    private Boolean actif = true;

    @Column(name = "created_at")
    private OffsetDateTime createdAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_role_role", referencedColumnName = "id_role", insertable = false, updatable = false)
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
