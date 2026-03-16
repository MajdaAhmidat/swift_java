package com.stage.swift.service.entity.impl;

import com.stage.swift.entity.Adresse;
import com.stage.swift.repository.AdresseRepository;
import com.stage.swift.service.entity.AdresseService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class AdresseServiceImpl implements AdresseService {

    private final AdresseRepository repository;

    public AdresseServiceImpl(AdresseRepository repository) {
        this.repository = repository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Adresse> findAll() {
        return repository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Adresse> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public Adresse save(Adresse entity) {
        return repository.save(entity);
    }

    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}
