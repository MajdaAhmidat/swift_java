package com.stage.swift.service.entity;

import com.stage.swift.entity.VirementRecuHisto;

import java.util.List;
import java.util.Optional;

public interface VirementRecuHistoService {

    List<VirementRecuHisto> findAll();

    Optional<VirementRecuHisto> findById(VirementRecuHisto.VirementRecuHistoPK id);

    VirementRecuHisto save(VirementRecuHisto entity);

    void deleteById(VirementRecuHisto.VirementRecuHistoPK id);
}
