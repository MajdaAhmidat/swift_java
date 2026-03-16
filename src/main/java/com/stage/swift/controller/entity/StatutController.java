package com.stage.swift.controller.entity;

import com.stage.swift.dto.entity.StatutDTO;
import com.stage.swift.entity.Statut;
import com.stage.swift.mapper.EntityMapper;
import com.stage.swift.service.entity.StatutService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

/** REST Controller pour Statut. */
@RestController
@RequestMapping("/api/statuts")
@CrossOrigin(origins = "*")
public class StatutController {

    private final StatutService statutService;
    private final EntityMapper mapper;

    public StatutController(StatutService statutService, EntityMapper mapper) {
        this.statutService = statutService;
        this.mapper = mapper;
    }

    @GetMapping
    public ResponseEntity<List<StatutDTO>> findAll() {
        List<StatutDTO> list = statutService.findAll().stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(list);
    }

    @GetMapping("/{id}")
    public ResponseEntity<StatutDTO> findById(@PathVariable Long id) {
        return statutService.findById(id)
                .map(mapper::toDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<StatutDTO> create(@Valid @RequestBody StatutDTO dto) {
        Statut entity = mapper.toEntity(dto);
        entity = statutService.save(entity);
        return ResponseEntity.status(HttpStatus.CREATED).body(mapper.toDTO(entity));
    }

    @PutMapping("/{id}")
    public ResponseEntity<StatutDTO> update(@PathVariable Long id, @Valid @RequestBody StatutDTO dto) {
        if (!statutService.findById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        dto.setIdStatut(id);
        Statut entity = mapper.toEntity(dto);
        entity = statutService.save(entity);
        return ResponseEntity.ok(mapper.toDTO(entity));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (!statutService.findById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        statutService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
