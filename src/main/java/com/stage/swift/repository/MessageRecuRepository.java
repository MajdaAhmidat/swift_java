package com.stage.swift.repository;

import com.stage.swift.entity.MessageRecu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface MessageRecuRepository extends JpaRepository<MessageRecu, MessageRecu.MessageRecuPK> {

    @Query("SELECT COALESCE(MAX(m.idMsgRecu), 0) + 1 FROM MessageRecu m")
    Long nextIdMsgRecu();

    boolean existsByIdVrtRecuVirementRecu(Long idVrtRecuVirementRecu);
}
