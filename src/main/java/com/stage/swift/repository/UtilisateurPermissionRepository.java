package com.stage.swift.repository;

import com.stage.swift.entity.UtilisateurPermission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UtilisateurPermissionRepository extends JpaRepository<UtilisateurPermission, Integer> {
    List<UtilisateurPermission> findByIdUser(Integer idUser);
    void deleteByIdUser(Integer idUser);
}
