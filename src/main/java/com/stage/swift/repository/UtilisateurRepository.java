package com.stage.swift.repository;

import com.stage.swift.entity.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UtilisateurRepository extends JpaRepository<Utilisateur, Utilisateur.UtilisateurPK> {
    Optional<Utilisateur> findByLogin(String login);

    @Query("SELECT u FROM Utilisateur u LEFT JOIN FETCH u.role WHERE u.login = :login")
    Optional<Utilisateur> findByLoginWithRole(@Param("login") String login);

    boolean existsByLogin(String login);

    @Query("SELECT u FROM Utilisateur u LEFT JOIN FETCH u.role WHERE u.idUser = :idUser")
    Optional<Utilisateur> findByIdUser(@Param("idUser") Integer idUser);

    @Query("SELECT DISTINCT u FROM Utilisateur u LEFT JOIN FETCH u.role ORDER BY u.idUser")
    List<Utilisateur> findAllWithRole();
}
