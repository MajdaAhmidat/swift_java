package com.stage.swift.repository;

import com.stage.swift.entity.Bic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BicRepository extends JpaRepository<Bic, Long> {

    /** Trouve un BIC par code (ordonnateur ou bénéficiaire) pour résoudre le SOP associé. */
    Optional<Bic> findFirstByBicOrdonnateurOrBicBeneficiare(String bicOrdonnateur, String bicBeneficiare);

    /** Génère le prochain code_bic (PK) en base. */
    @Query("SELECT COALESCE(MAX(b.codeBic), 0) + 1 FROM Bic b")
    Long nextCodeBic();
}
