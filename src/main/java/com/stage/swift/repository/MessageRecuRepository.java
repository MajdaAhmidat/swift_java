package com.stage.swift.repository;

import com.stage.swift.entity.MessageRecu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface MessageRecuRepository extends JpaRepository<MessageRecu, MessageRecu.MessageRecuPK> {

    @Query("SELECT COALESCE(MAX(m.idMsgRecu), 0) + 1 FROM MessageRecu m")
    Long nextIdMsgRecu();

    boolean existsByIdVrtRecuVirementRecu(Long idVrtRecuVirementRecu);

    Optional<MessageRecu> findTopByIdVrtRecuVirementRecuOrderByIdMsgRecuDesc(Long idVrtRecuVirementRecu);

    List<MessageRecu> findByIdVrtRecuVirementRecuOrderByIdMsgRecuAsc(Long idVrtRecuVirementRecu);

    @Modifying
    @Query(value = "UPDATE swift.message_recu SET sop = :newSop, id_sop_virement_recu = :newSop WHERE id_vrt_recu_virement_recu = :idVrtRecu", nativeQuery = true)
    int updateSopByIdVrtRecu(@Param("idVrtRecu") Long idVrtRecu, @Param("newSop") Long newSop);
}
