package com.stage.swift.repository;

import com.stage.swift.entity.TypeMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TypeMessageRepository extends JpaRepository<TypeMessage, Long> {
}
