package com.stage.swift.repository;

import com.stage.swift.entity.MessageXmlArchive;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MessageXmlArchiveRepository extends JpaRepository<MessageXmlArchive, Long> {

    Optional<MessageXmlArchive> findTopByIdVrtEmisOrderByCreatedAtDesc(Long idVrtEmis);

    Optional<MessageXmlArchive> findTopByIdVrtRecuOrderByCreatedAtDesc(Long idVrtRecu);

    boolean existsByIdVrtEmisAndPayloadHash(Long idVrtEmis, String payloadHash);

    boolean existsByIdVrtRecuAndPayloadHash(Long idVrtRecu, String payloadHash);
}
