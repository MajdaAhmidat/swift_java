package com.stage.swift.repository;

import com.stage.swift.entity.Statut;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StatutRepository extends JpaRepository<Statut, Long> {

    java.util.Optional<Statut> findByCodeStatut(String codeStatut);
}
