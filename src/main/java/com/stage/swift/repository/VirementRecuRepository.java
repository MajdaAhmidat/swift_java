package com.stage.swift.repository;

import com.stage.swift.entity.VirementRecu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VirementRecuRepository extends JpaRepository<VirementRecu, VirementRecu.VirementRecuPK> {

    @Query("SELECT COALESCE(MAX(v.idVrtRecu), 0) + 1 FROM VirementRecu v")
    Long nextIdVrtRecu();

    List<VirementRecu> findByReference(String reference);

    @Query("SELECT v FROM VirementRecu v WHERE TRIM(v.reference) = TRIM(:ref)")
    List<VirementRecu> findByReferenceTrimmed(@Param("ref") String ref);
}
