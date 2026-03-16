package com.stage.swift.controller.entity;

import com.stage.swift.dto.entity.AdresseDTO;
import com.stage.swift.entity.Adresse;
import com.stage.swift.mapper.EntityMapper;
import com.stage.swift.service.entity.AdresseService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/adresses")
@CrossOrigin(origins = "*")
public class AdresseController {

    private final AdresseService adresseService;
    private final EntityMapper mapper;

    public AdresseController(AdresseService adresseService, EntityMapper mapper) {
        this.adresseService = adresseService;
        this.mapper = mapper;
    }

    @GetMapping
    public ResponseEntity<List<AdresseDTO>> findAll() {
        List<AdresseDTO> list = adresseService.findAll().stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(list);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AdresseDTO> findById(@PathVariable Long id) {
        return adresseService.findById(id)
                .map(mapper::toDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<AdresseDTO> create(@Valid @RequestBody AdresseDTO dto) {
        Adresse entity = mapper.toEntity(dto);
        entity = adresseService.save(entity);
        return ResponseEntity.status(HttpStatus.CREATED).body(mapper.toDTO(entity));
    }

    @PutMapping("/{id}")
    public ResponseEntity<AdresseDTO> update(@PathVariable Long id, @Valid @RequestBody AdresseDTO dto) {
        if (!adresseService.findById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        dto.setIdAdresse(id);
        Adresse entity = mapper.toEntity(dto);
        entity = adresseService.save(entity);
        return ResponseEntity.ok(mapper.toDTO(entity));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (!adresseService.findById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        adresseService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
