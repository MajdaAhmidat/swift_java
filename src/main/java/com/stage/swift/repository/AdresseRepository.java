package com.stage.swift.repository;

import com.stage.swift.entity.Adresse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AdresseRepository extends JpaRepository<Adresse, Long> {

    @Query("select coalesce(max(a.idAdresse), 0) + 1 from Adresse a")
    Long nextIdAdresse();

    Optional<Adresse> findByLigne1AndLigne2AndVilleAndCodePostalAndPays(
            String ligne1,
            String ligne2,
            String ville,
            String codePostal,
            String pays
    );

    /** Même adresse textuelle mais avec le type (DBTR=1, CDTR=2) pour distinguer créditeur/débiteur. */
    Optional<Adresse> findByLigne1AndLigne2AndVilleAndCodePostalAndPaysAndIdTypeAdresse(
            String ligne1,
            String ligne2,
            String ville,
            String codePostal,
            String pays,
            Long idTypeAdresse
    );
}
