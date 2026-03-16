package com.stage.swift.service.entity;

import com.stage.swift.entity.VirementEmis;

import java.util.List;
import java.util.Optional;

public interface VirementEmisService {

    List<VirementEmis> findAll();

    Optional<VirementEmis> findById(VirementEmis.VirementEmisPK id);

    VirementEmis save(VirementEmis entity);

    void deleteById(VirementEmis.VirementEmisPK id);
}
