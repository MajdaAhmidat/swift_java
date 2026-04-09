package com.stage.swift.repository;

import com.stage.swift.entity.Sop;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SopRepository extends JpaRepository<Sop, Long> {
    @Query("SELECT s FROM Sop s WHERE UPPER(TRIM(s.libelleSop)) = UPPER(TRIM(:libelle))")
    Optional<Sop> findByLibelleSopNormalized(@Param("libelle") String libelle);

    @Query("SELECT COALESCE(MAX(s.id), 0) + 1 FROM Sop s")
    Long nextIdSop();
}
