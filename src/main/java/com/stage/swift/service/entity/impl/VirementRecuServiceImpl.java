package com.stage.swift.service.entity.impl;

import com.stage.swift.entity.VirementRecu;
import com.stage.swift.repository.VirementRecuRepository;
import com.stage.swift.service.entity.VirementRecuService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class VirementRecuServiceImpl implements VirementRecuService {

    private final VirementRecuRepository repository;

    public VirementRecuServiceImpl(VirementRecuRepository repository) {
        this.repository = repository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<VirementRecu> findAll() {
        return repository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<VirementRecu> findById(VirementRecu.VirementRecuPK id) {
        return repository.findById(id);
    }

    @Override
    public VirementRecu save(VirementRecu entity) {
        return repository.save(entity);
    }

    @Override
    public void deleteById(VirementRecu.VirementRecuPK id) {
        repository.deleteById(id);
    }
}
