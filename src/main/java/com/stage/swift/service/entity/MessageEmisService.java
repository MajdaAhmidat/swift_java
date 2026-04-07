package com.stage.swift.service.entity;

import com.stage.swift.entity.MessageEmis;

import java.util.List;
import java.util.Optional;

public interface MessageEmisService {

    List<MessageEmis> findAll();

    Optional<MessageEmis> findById(MessageEmis.MessageEmisPK id);

    MessageEmis save(MessageEmis entity);

    void deleteById(MessageEmis.MessageEmisPK id);

    List<MessageEmis> findByVirementEmisId(Long idVrtEmis);

    Optional<String> generateXmlByVirementEmisId(Long idVrtEmis);

    Optional<String> generateXmlByMessageEmisPk(MessageEmis.MessageEmisPK id);
}
