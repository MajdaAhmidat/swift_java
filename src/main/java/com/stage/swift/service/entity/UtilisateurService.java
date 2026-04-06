package com.stage.swift.service.entity;

import com.stage.swift.entity.Utilisateur;

import java.util.List;
import java.util.Optional;

public interface UtilisateurService {

    List<Utilisateur> findAll();

    Optional<Utilisateur> findById(Utilisateur.UtilisateurPK id);

    Optional<Utilisateur> findByIdUser(Integer idUser);

    Utilisateur save(Utilisateur entity);

    void deleteById(Utilisateur.UtilisateurPK id);
}
