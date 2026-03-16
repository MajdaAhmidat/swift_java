package com.stage.swift.service.entity;

import com.stage.swift.entity.Role;

import java.util.List;
import java.util.Optional;

public interface RoleService {

    List<Role> findAll();

    Optional<Role> findById(Integer id);

    Role save(Role entity);

    void deleteById(Integer id);
}
