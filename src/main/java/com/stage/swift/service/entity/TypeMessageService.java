package com.stage.swift.service.entity;

import com.stage.swift.entity.TypeMessage;

import java.util.List;
import java.util.Optional;

public interface TypeMessageService {

    List<TypeMessage> findAll();

    Optional<TypeMessage> findById(Long id);

    TypeMessage save(TypeMessage entity);

    void deleteById(Long id);
}
