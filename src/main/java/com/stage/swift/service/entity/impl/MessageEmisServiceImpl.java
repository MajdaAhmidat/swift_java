package com.stage.swift.service.entity.impl;

import com.stage.swift.entity.MessageEmis;
import com.stage.swift.repository.MessageEmisRepository;
import com.stage.swift.service.entity.MessageEmisService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
}
