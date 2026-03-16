package com.stage.swift.repository;

import com.stage.swift.entity.VirementEmisHisto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface VirementEmisHistoRepository extends JpaRepository<VirementEmisHisto, Long> {

    @Query("SELECT COALESCE(MAX(h.idVrtEmisHisto), 0) + 1 FROM VirementEmisHisto h")
    Long nextIdVrtEmisHisto();
}
