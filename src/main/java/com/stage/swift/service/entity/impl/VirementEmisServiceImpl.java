package com.stage.swift.service.entity.impl;

import com.stage.swift.entity.VirementEmis;
import com.stage.swift.repository.VirementEmisRepository;
import com.stage.swift.service.entity.VirementEmisService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class VirementEmisServiceImpl implements VirementEmisService {

    private final VirementEmisRepository repository;

    public VirementEmisServiceImpl(VirementEmisRepository repository) {
        this.repository = repository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<VirementEmis> findAll() {
        return repository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<VirementEmis> findById(VirementEmis.VirementEmisPK id) {
        return repository.findById(id);
    }

    @Override
    public VirementEmis save(VirementEmis entity) {
        return repository.save(entity);
    }

    @Override
    public void deleteById(VirementEmis.VirementEmisPK id) {
        repository.deleteById(id);
    }
}
