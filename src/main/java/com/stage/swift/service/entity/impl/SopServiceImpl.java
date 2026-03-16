package com.stage.swift.service.entity.impl;

import com.stage.swift.entity.Sop;
import com.stage.swift.repository.SopRepository;
import com.stage.swift.service.entity.SopService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class SopServiceImpl implements SopService {

    private final SopRepository repository;

    public SopServiceImpl(SopRepository repository) {
        this.repository = repository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Sop> findAll() {
        return repository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Sop> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public Sop save(Sop entity) {
        return repository.save(entity);
    }

    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}
