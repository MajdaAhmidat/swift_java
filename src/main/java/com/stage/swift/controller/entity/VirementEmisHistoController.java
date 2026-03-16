package com.stage.swift.controller.entity;

import com.stage.swift.dto.entity.VirementEmisHistoDTO;
import com.stage.swift.entity.VirementEmisHisto;
import com.stage.swift.mapper.EntityMapper;
import com.stage.swift.service.entity.VirementEmisHistoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

/** REST Controller pour VIREMENT_EMIS_HISTO (historique des virements émis). */
@RestController
@RequestMapping("/api/virements-emis-histo")
@CrossOrigin(origins = "*")
public class VirementEmisHistoController {

    private final VirementEmisHistoService virementEmisHistoService;
    private final EntityMapper mapper;

    public VirementEmisHistoController(VirementEmisHistoService virementEmisHistoService, EntityMapper mapper) {
        this.virementEmisHistoService = virementEmisHistoService;
        this.mapper = mapper;
    }

    @GetMapping
    public ResponseEntity<List<VirementEmisHistoDTO>> findAll() {
        List<VirementEmisHistoDTO> list = virementEmisHistoService.findAll().stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(list);
    }

    @GetMapping("/by-id")
    public ResponseEntity<VirementEmisHistoDTO> findById(@RequestParam Long idVrtEmisHisto) {
        return virementEmisHistoService.findById(idVrtEmisHisto)
                .map(mapper::toDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<VirementEmisHistoDTO> create(@Valid @RequestBody VirementEmisHistoDTO dto) {
        VirementEmisHisto entity = mapper.toEntity(dto);
        entity = virementEmisHistoService.save(entity);
        return ResponseEntity.status(HttpStatus.CREATED).body(mapper.toDTO(entity));
    }

    @PutMapping
    public ResponseEntity<VirementEmisHistoDTO> update(@Valid @RequestBody VirementEmisHistoDTO dto) {
        Long id = dto.getIdVrtEmisHisto();
        if (!virementEmisHistoService.findById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        VirementEmisHisto entity = mapper.toEntity(dto);
        entity = virementEmisHistoService.save(entity);
        return ResponseEntity.ok(mapper.toDTO(entity));
    }

    @DeleteMapping
    public ResponseEntity<Void> delete(@RequestParam Long idVrtEmisHisto) {
        if (!virementEmisHistoService.findById(idVrtEmisHisto).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        virementEmisHistoService.deleteById(idVrtEmisHisto);
        return ResponseEntity.noContent().build();
    }
}
