package com.stage.swift.service.entity;

import com.stage.swift.entity.Adresse;

import java.util.List;
import java.util.Optional;

public interface AdresseService {

    List<Adresse> findAll();

    Optional<Adresse> findById(Long id);

    Adresse save(Adresse entity);

    void deleteById(Long id);
}
