package com.stage.swift.controller.entity;

import com.stage.swift.dto.entity.BicDTO;
import com.stage.swift.entity.Bic;
import com.stage.swift.mapper.EntityMapper;
import com.stage.swift.service.entity.BicService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/bics")
@CrossOrigin(origins = "*")
public class BicController {

    private final BicService bicService;
    private final EntityMapper mapper;

    public BicController(BicService bicService, EntityMapper mapper) {
        this.bicService = bicService;
        this.mapper = mapper;
    }

    @GetMapping
    public ResponseEntity<List<BicDTO>> findAll() {
        List<BicDTO> list = bicService.findAll().stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(list);
    }

    @GetMapping("/{codeBic}")
    public ResponseEntity<BicDTO> findById(@PathVariable Long codeBic) {
        return bicService.findById(codeBic)
                .map(mapper::toDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<BicDTO> create(@Valid @RequestBody BicDTO dto) {
        Bic entity = mapper.toEntity(dto);
        entity = bicService.save(entity);
        return ResponseEntity.status(HttpStatus.CREATED).body(mapper.toDTO(entity));
    }

    @PutMapping("/{codeBic}")
    public ResponseEntity<BicDTO> update(@PathVariable Long codeBic, @Valid @RequestBody BicDTO dto) {
        if (!bicService.findById(codeBic).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        dto.setCodeBic(codeBic);
        Bic entity = mapper.toEntity(dto);
        entity = bicService.save(entity);
        return ResponseEntity.ok(mapper.toDTO(entity));
    }

    @DeleteMapping("/{codeBic}")
    public ResponseEntity<Void> delete(@PathVariable Long codeBic) {
        if (!bicService.findById(codeBic).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        bicService.deleteById(codeBic);
        return ResponseEntity.noContent().build();
    }
}
