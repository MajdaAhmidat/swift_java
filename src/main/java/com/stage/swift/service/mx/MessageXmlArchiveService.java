package com.stage.swift.service.mx;

import java.util.Optional;

public interface MessageXmlArchiveService {
    void archiveForVirementEmis(Long idVrtEmis, String flowType, String messageType, String rawXml);
    void archiveForVirementRecu(Long idVrtRecu, String flowType, String messageType, String rawXml);
    Optional<String> findLatestXmlByVirementEmisId(Long idVrtEmis);
    Optional<String> findLatestXmlByVirementRecuId(Long idVrtRecu);
}
