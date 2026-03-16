package com.stage.swift.service.entity.impl;

import com.stage.swift.entity.Statut;
import com.stage.swift.repository.StatutRepository;
import com.stage.swift.service.entity.StatutService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class StatutServiceImpl implements StatutService {

    private final StatutRepository repository;

    public StatutServiceImpl(StatutRepository repository) {
        this.repository = repository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Statut> findAll() {
        return repository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Statut> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public Statut save(Statut entity) {
        return repository.save(entity);
    }

    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}
