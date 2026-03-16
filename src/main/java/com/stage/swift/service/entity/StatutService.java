package com.stage.swift.service.entity;

import com.stage.swift.entity.Statut;

import java.util.List;
import java.util.Optional;

public interface StatutService {

    List<Statut> findAll();

    Optional<Statut> findById(Long id);

    Statut save(Statut entity);

    void deleteById(Long id);
}
