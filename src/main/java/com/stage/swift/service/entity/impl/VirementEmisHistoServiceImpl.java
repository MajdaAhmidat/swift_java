package com.stage.swift.service.entity.impl;

import com.stage.swift.entity.VirementEmisHisto;
import com.stage.swift.repository.VirementEmisHistoRepository;
import com.stage.swift.service.entity.VirementEmisHistoService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class VirementEmisHistoServiceImpl implements VirementEmisHistoService {

    private final VirementEmisHistoRepository repository;

    public VirementEmisHistoServiceImpl(VirementEmisHistoRepository repository) {
        this.repository = repository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<VirementEmisHisto> findAll() {
        return repository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<VirementEmisHisto> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public VirementEmisHisto save(VirementEmisHisto entity) {
        return repository.save(entity);
    }

    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}
