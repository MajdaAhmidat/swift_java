package com.stage.swift.service.entity.impl;

import com.stage.swift.entity.MessageRecu;
import com.stage.swift.repository.MessageRecuRepository;
import com.stage.swift.service.entity.MessageRecuService;
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
public class MessageRecuServiceImpl implements MessageRecuService {

    private final MessageRecuRepository repository;

    public MessageRecuServiceImpl(MessageRecuRepository repository) {
        this.repository = repository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<MessageRecu> findAll() {
        return repository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<MessageRecu> findById(MessageRecu.MessageRecuPK id) {
        return repository.findById(id);
    }

    @Override
    public MessageRecu save(MessageRecu entity) {
        return repository.save(entity);
    }

    @Override
    public void deleteById(MessageRecu.MessageRecuPK id) {
        repository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<MessageRecu> findByVirementRecuId(Long idVrtRecu) {
        return repository.findByIdVrtRecuVirementRecuOrderByIdMsgRecuAsc(idVrtRecu);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<String> generateXmlByVirementRecuId(Long idVrtRecu) {
        return repository.findTopByIdVrtRecuVirementRecuOrderByIdMsgRecuDesc(idVrtRecu)
                .flatMap(this::readXmlFromStoredPath);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<String> generateXmlByMessageRecuPk(MessageRecu.MessageRecuPK id) {
        return repository.findById(id).flatMap(this::readXmlFromStoredPath);
    }

    private Optional<String> readXmlFromStoredPath(MessageRecu message) {
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
