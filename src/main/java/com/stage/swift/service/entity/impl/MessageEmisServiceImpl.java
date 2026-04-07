package com.stage.swift.service.entity.impl;

import com.stage.swift.entity.MessageEmis;
import com.stage.swift.repository.MessageEmisRepository;
import com.stage.swift.service.entity.MessageEmisService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class MessageEmisServiceImpl implements MessageEmisService {

    private final MessageEmisRepository repository;

    public MessageEmisServiceImpl(MessageEmisRepository repository) {
        this.repository = repository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<MessageEmis> findAll() {
        return repository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<MessageEmis> findById(MessageEmis.MessageEmisPK id) {
        return repository.findById(id);
    }

    @Override
    public MessageEmis save(MessageEmis entity) {
        return repository.save(entity);
    }

    @Override
    public void deleteById(MessageEmis.MessageEmisPK id) {
        repository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<MessageEmis> findByVirementEmisId(Long idVrtEmis) {
        return repository.findByIdVrtEmisOrderByIdMsgEmisAsc(idVrtEmis);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<String> generateXmlByVirementEmisId(Long idVrtEmis) {
        return repository.findTopByIdVrtEmisOrderByIdMsgEmisDesc(idVrtEmis)
                .flatMap(this::readXmlFromStoredPath);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<String> generateXmlByMessageEmisPk(MessageEmis.MessageEmisPK id) {
        return repository.findById(id).flatMap(this::readXmlFromStoredPath);
    }

    private Optional<String> readXmlFromStoredPath(MessageEmis message) {
        String pathValue = message.getPath();
        if (pathValue == null || pathValue.trim().isEmpty()) {
            return Optional.empty();
        }
        try {
            Path path = Paths.get(pathValue);
            if (!Files.exists(path) || !Files.isRegularFile(path)) {
                return Optional.empty();
            }
            byte[] bytes = Files.readAllBytes(path);
            return Optional.of(new String(bytes, StandardCharsets.UTF_8));
        } catch (Exception ignored) {
            return Optional.empty();
        }
    }
}
