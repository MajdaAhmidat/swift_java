package com.stage.swift.repository;

import com.stage.swift.entity.RolePermission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RolePermissionRepository extends JpaRepository<RolePermission, Integer> {
    List<RolePermission> findByRoleIdRole(Integer roleIdRole);
    void deleteByRoleIdRole(Integer roleIdRole);
}
