package com.stage.swift.service.entity;

import com.stage.swift.entity.MessageRecu;

import java.util.List;
import java.util.Optional;

public interface MessageRecuService {

    List<MessageRecu> findAll();

    Optional<MessageRecu> findById(MessageRecu.MessageRecuPK id);

    MessageRecu save(MessageRecu entity);

    void deleteById(MessageRecu.MessageRecuPK id);

    List<MessageRecu> findByVirementRecuId(Long idVrtRecu);

    Optional<String> generateXmlByVirementRecuId(Long idVrtRecu);

    Optional<String> generateXmlByMessageRecuPk(MessageRecu.MessageRecuPK id);
}
