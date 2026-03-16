package com.stage.swift.repository;

import com.stage.swift.entity.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UtilisateurRepository extends JpaRepository<Utilisateur, Utilisateur.UtilisateurPK> {
}
