package com.stage.swift.service.entity.impl;

import com.stage.swift.entity.Bic;
import com.stage.swift.repository.BicRepository;
import com.stage.swift.service.entity.BicService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class BicServiceImpl implements BicService {

    private final BicRepository repository;

    public BicServiceImpl(BicRepository repository) {
        this.repository = repository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Bic> findAll() {
        return repository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Bic> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public Bic save(Bic entity) {
        return repository.save(entity);
    }

    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}
