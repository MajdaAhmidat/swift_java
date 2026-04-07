package com.stage.swift.repository;

import com.stage.swift.entity.VirementEmis;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface VirementEmisRepository extends JpaRepository<VirementEmis, VirementEmis.VirementEmisPK> {

    @Query("SELECT COALESCE(MAX(v.idVrtEmis), 0) + 1 FROM VirementEmis v")
    Long nextIdVrtEmis();

    @Query("SELECT v FROM VirementEmis v WHERE v.reference = :reference AND v.idSop = :idSop")
    List<VirementEmis> findByReferenceAndIdSop(@Param("reference") String reference, @Param("idSop") Long idSop);

    /** Recherche les virements par référence uniquement (utilisé pour appliquer le statut SAA). */
    List<VirementEmis> findByReference(String reference);

    /** Recherche d'un virement emission par identifiant technique uniquement. */
    Optional<VirementEmis> findByIdVrtEmis(Long idVrtEmis);

    /** Recherche par référence en ignorant espaces début/fin (TRIM) pour rapprochement .ack / virement_emis. */
    @Query("SELECT v FROM VirementEmis v WHERE TRIM(v.reference) = TRIM(:ref)")
    List<VirementEmis> findByReferenceTrimmed(@Param("ref") String ref);

    /**
     * Recherche d'un virement émission par UETR uniquement.
     * Utiliser PageRequest.of(0, 1) pour récupérer le plus récent.
     */
    @Query("SELECT v FROM VirementEmis v " +
           "WHERE LOWER(TRIM(v.uetr)) = LOWER(TRIM(:uetr)) " +
           "ORDER BY v.idVrtEmis DESC")
    List<VirementEmis> findByUetrForDedup(@Param("uetr") String uetr, Pageable pageable);

    @Modifying
    @Query(value = "UPDATE swift.virement_emis SET id_adresse = :idAdresse WHERE id_vrt_emis = :idVrtEmis", nativeQuery = true)
    int updateAdresseByIdVrtEmis(@Param("idVrtEmis") Long idVrtEmis, @Param("idAdresse") Long idAdresse);
}
