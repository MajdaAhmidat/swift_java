package com.stage.swift.service.mx;

import com.prowidesoftware.swift.model.MessageStandardType;
import com.stage.swift.entity.VirementRecu;

/**
 * Service d'orchestration pour le traitement des messages MX (PACS008 / PACS009).
 */
public interface MessageProcessorService {

    VirementRecu process(String content, MessageStandardType messageType);
}
