package com.stage.swift.repository;

import com.stage.swift.entity.MessageEmis;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface MessageEmisRepository extends JpaRepository<MessageEmis, MessageEmis.MessageEmisPK> {

    @Query("SELECT COALESCE(MAX(m.idMsgEmis), 0) + 1 FROM MessageEmis m")
    Long nextIdMsgEmis();

    Optional<MessageEmis> findTopByIdVrtEmisOrderByIdMsgEmisDesc(Long idVrtEmis);

    List<MessageEmis> findByIdVrtEmisOrderByIdMsgEmisAsc(Long idVrtEmis);
}
