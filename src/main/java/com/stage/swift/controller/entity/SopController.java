package com.stage.swift.controller.entity;

import com.stage.swift.dto.entity.SopDTO;
import com.stage.swift.entity.Sop;
import com.stage.swift.mapper.EntityMapper;
import com.stage.swift.service.entity.SopService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

/** REST Controller pour Sop (SOP has_many VIREMENT_EMIS, VIREMENT_RECU). */
@RestController
@RequestMapping("/api/sops")
@CrossOrigin(origins = "*")
public class SopController {

    private final SopService sopService;
    private final EntityMapper mapper;

    public SopController(SopService sopService, EntityMapper mapper) {
        this.sopService = sopService;
        this.mapper = mapper;
    }

    @GetMapping
    public ResponseEntity<List<SopDTO>> findAll() {
        List<SopDTO> list = sopService.findAll().stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(list);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SopDTO> findById(@PathVariable Long id) {
        return sopService.findById(id)
                .map(mapper::toDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<SopDTO> create(@Valid @RequestBody SopDTO dto) {
        Sop entity = mapper.toEntity(dto);
        entity = sopService.save(entity);
        return ResponseEntity.status(HttpStatus.CREATED).body(mapper.toDTO(entity));
    }

    @PutMapping("/{id}")
    public ResponseEntity<SopDTO> update(@PathVariable Long id, @Valid @RequestBody SopDTO dto) {
        if (!sopService.findById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        dto.setId(id);
        Sop entity = mapper.toEntity(dto);
        entity = sopService.save(entity);
        return ResponseEntity.ok(mapper.toDTO(entity));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (!sopService.findById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        sopService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
