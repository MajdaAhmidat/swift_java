package com.stage.swift.service.mx.impl;

import com.prowidesoftware.swift.model.MessageStandardType;
import com.stage.swift.entity.VirementRecu;
import com.stage.swift.service.mx.Iso20022MessageService;
import com.stage.swift.service.mx.MessageProcessorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * Implémentation de {@link MessageProcessorService}.
 * Délègue le parsing et la persistance à {@link Iso20022MessageService}.
 */
@Service
public class MessageProcessorServiceImpl implements MessageProcessorService {

    private static final Logger logger = LoggerFactory.getLogger(MessageProcessorServiceImpl.class);

    private final Iso20022MessageService iso20022MessageService;

    public MessageProcessorServiceImpl(Iso20022MessageService iso20022MessageService) {
        this.iso20022MessageService = iso20022MessageService;
    }

    @Override
    public VirementRecu process(String content, MessageStandardType messageType) {
        return process(content, messageType, null);
    }

    @Override
    public VirementRecu process(String content, MessageStandardType messageType, String sourceFilePath) {
        if (MessageStandardType.MX.equals(messageType)) {
            return iso20022MessageService.parseAndSave(content, sourceFilePath);
        }
        logger.warn("[MessageProcessorService] Type {} non supporté (seul MX est persisté)", messageType);
        return null;
    }

}
