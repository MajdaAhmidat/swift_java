package com.stage.swift.service.entity.impl;

import com.stage.swift.entity.MessageRecu;
import com.stage.swift.repository.MessageRecuRepository;
import com.stage.swift.service.entity.MessageRecuService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
}
