package com.stage.swift.service.entity.impl;

import com.stage.swift.entity.TypeMessage;
import com.stage.swift.repository.TypeMessageRepository;
import com.stage.swift.service.entity.TypeMessageService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class TypeMessageServiceImpl implements TypeMessageService {

    private final TypeMessageRepository repository;

    public TypeMessageServiceImpl(TypeMessageRepository repository) {
        this.repository = repository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<TypeMessage> findAll() {
        return repository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<TypeMessage> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public TypeMessage save(TypeMessage entity) {
        return repository.save(entity);
    }

    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}
