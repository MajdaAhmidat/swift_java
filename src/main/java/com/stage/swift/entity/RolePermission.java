package com.stage.swift.entity;

import javax.persistence.*;

@Entity
@Table(schema = "swift", name = "role_permission")
public class RolePermission {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "id_role", nullable = false)
    private Integer roleIdRole;

    @Column(name = "permission_id", nullable = false)
    private Integer permissionId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getRoleIdRole() {
        return roleIdRole;
    }

    public void setRoleIdRole(Integer roleIdRole) {
        this.roleIdRole = roleIdRole;
    }

    public Integer getPermissionId() {
        return permissionId;
    }

    public void setPermissionId(Integer permissionId) {
        this.permissionId = permissionId;
    }
}
