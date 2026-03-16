package com.stage.swift.service.entity;

import com.stage.swift.entity.Utilisateur;

import java.util.List;
import java.util.Optional;

public interface UtilisateurService {

    List<Utilisateur> findAll();

    Optional<Utilisateur> findById(Utilisateur.UtilisateurPK id);

    Utilisateur save(Utilisateur entity);

    void deleteById(Utilisateur.UtilisateurPK id);
}
