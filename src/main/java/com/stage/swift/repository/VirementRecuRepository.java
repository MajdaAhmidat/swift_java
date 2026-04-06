package com.stage.swift.repository;

import com.stage.swift.entity.VirementRecu;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface VirementRecuRepository extends JpaRepository<VirementRecu, VirementRecu.VirementRecuPK> {

    @Query("SELECT COALESCE(MAX(v.idVrtRecu), 0) + 1 FROM VirementRecu v")
    Long nextIdVrtRecu();

    List<VirementRecu> findByReference(String reference);

    @Query("SELECT v FROM VirementRecu v WHERE TRIM(v.reference) = TRIM(:ref)")
    List<VirementRecu> findByReferenceTrimmed(@Param("ref") String ref);

    /**
     * Recherche par identifiant technique uniquement.
     */
    Optional<VirementRecu> findByIdVrtRecu(Long idVrtRecu);

    /**
     * Recherche d'un virement reçu par UETR uniquement.
     * Utiliser PageRequest.of(0, 1) pour récupérer le plus récent.
     */
    @Query("SELECT v FROM VirementRecu v " +
           "WHERE TRIM(v.uetr) = TRIM(:uetr) " +
           "ORDER BY v.idVrtRecu DESC")
    List<VirementRecu> findByUetrForDedup(@Param("uetr") String uetr, Pageable pageable);

    @Modifying
    @Query(value = "UPDATE swift.virement_recu SET id_adresse = :idAdresse WHERE id_vrt_recu = :idVrtRecu", nativeQuery = true)
    int updateAdresseByIdVrtRecu(@Param("idVrtRecu") Long idVrtRecu, @Param("idAdresse") Long idAdresse);
}
