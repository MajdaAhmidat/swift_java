package com.stage.swift.service.entity;

import com.stage.swift.entity.VirementEmisHisto;

import java.util.List;
import java.util.Optional;

public interface VirementEmisHistoService {

    List<VirementEmisHisto> findAll();

    Optional<VirementEmisHisto> findById(Long id);

    VirementEmisHisto save(VirementEmisHisto entity);

    void deleteById(Long id);
}
