package com.stage.swift.service.entity.impl;

import com.stage.swift.entity.VirementRecuHisto;
import com.stage.swift.repository.VirementRecuHistoRepository;
import com.stage.swift.service.entity.VirementRecuHistoService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class VirementRecuHistoServiceImpl implements VirementRecuHistoService {

    private final VirementRecuHistoRepository repository;

    public VirementRecuHistoServiceImpl(VirementRecuHistoRepository repository) {
        this.repository = repository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<VirementRecuHisto> findAll() {
        return repository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<VirementRecuHisto> findById(VirementRecuHisto.VirementRecuHistoPK id) {
        return repository.findById(id);
    }

    @Override
    public VirementRecuHisto save(VirementRecuHisto entity) {
        return repository.save(entity);
    }

    @Override
    public void deleteById(VirementRecuHisto.VirementRecuHistoPK id) {
        repository.deleteById(id);
    }
}
