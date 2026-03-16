package com.stage.swift.service.entity;

import com.stage.swift.entity.Sop;

import java.util.List;
import java.util.Optional;

public interface SopService {

    List<Sop> findAll();

    Optional<Sop> findById(Long id);

    Sop save(Sop entity);

    void deleteById(Long id);
}
