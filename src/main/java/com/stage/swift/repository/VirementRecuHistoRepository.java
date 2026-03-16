package com.stage.swift.repository;

import com.stage.swift.entity.VirementRecuHisto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface VirementRecuHistoRepository extends JpaRepository<VirementRecuHisto, VirementRecuHisto.VirementRecuHistoPK> {

    @Query("SELECT COALESCE(MAX(h.idVrtRecuHisto), 0) + 1 FROM VirementRecuHisto h")
    Long nextIdVrtRecuHisto();
}
