package com.stage.swift.service.entity;

import com.stage.swift.entity.Bic;

import java.util.List;
import java.util.Optional;

public interface BicService {

    List<Bic> findAll();

    Optional<Bic> findById(Long id);

    Bic save(Bic entity);

    void deleteById(Long id);
}
