package com.stage.swift.service.entity;

import com.stage.swift.entity.VirementRecu;

import java.util.List;
import java.util.Optional;

public interface VirementRecuService {

    List<VirementRecu> findAll();

    Optional<VirementRecu> findById(VirementRecu.VirementRecuPK id);

    VirementRecu save(VirementRecu entity);

    void deleteById(VirementRecu.VirementRecuPK id);
}
