package com.stage.swift.controller.entity;

import com.stage.swift.dto.entity.VirementRecuDTO;
import com.stage.swift.entity.VirementRecu;
import com.stage.swift.mapper.EntityMapper;
import com.stage.swift.service.entity.VirementRecuService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/virements-recu")
@CrossOrigin(origins = "*")
public class VirementRecuController {

    private final VirementRecuService virementRecuService;
    private final EntityMapper mapper;

    public VirementRecuController(VirementRecuService virementRecuService, EntityMapper mapper) {
        this.virementRecuService = virementRecuService;
        this.mapper = mapper;
    }

    @GetMapping
    public ResponseEntity<List<VirementRecuDTO>> findAll() {
        List<VirementRecuDTO> list = virementRecuService.findAll().stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(list);
    }

    @GetMapping("/by-id")
    public ResponseEntity<VirementRecuDTO> findById(
            @RequestParam Long idVrtRecu,
            @RequestParam Long idStatutStatut,
            @RequestParam Long idAdresseAdresse,
            @RequestParam Long idSop,
            @RequestParam Long codeBicBic,
            @RequestParam Long codeMsgTypeMessage) {
        VirementRecu.VirementRecuPK pk = new VirementRecu.VirementRecuPK(
                idVrtRecu, idStatutStatut, idAdresseAdresse, idSop, codeBicBic, codeMsgTypeMessage);
        return virementRecuService.findById(pk)
                .map(mapper::toDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<VirementRecuDTO> create(@Valid @RequestBody VirementRecuDTO dto) {
        VirementRecu entity = mapper.toEntity(dto);
        entity = virementRecuService.save(entity);
        return ResponseEntity.status(HttpStatus.CREATED).body(mapper.toDTO(entity));
    }

    @PutMapping
    public ResponseEntity<VirementRecuDTO> update(@Valid @RequestBody VirementRecuDTO dto) {
        VirementRecu.VirementRecuPK pk = new VirementRecu.VirementRecuPK(
                dto.getIdVrtRecu(), dto.getIdStatutStatut(), dto.getIdAdresseAdresse(),
                dto.getIdSop(), dto.getCodeBicBic(), dto.getCodeMsgTypeMessage());
        if (!virementRecuService.findById(pk).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        VirementRecu entity = mapper.toEntity(dto);
        entity = virementRecuService.save(entity);
        return ResponseEntity.ok(mapper.toDTO(entity));
    }

    @DeleteMapping
    public ResponseEntity<Void> delete(
            @RequestParam Long idVrtRecu,
            @RequestParam Long idStatutStatut,
            @RequestParam Long idAdresseAdresse,
            @RequestParam Long idSop,
            @RequestParam Long codeBicBic,
            @RequestParam Long codeMsgTypeMessage) {
        VirementRecu.VirementRecuPK pk = new VirementRecu.VirementRecuPK(
                idVrtRecu, idStatutStatut, idAdresseAdresse, idSop, codeBicBic, codeMsgTypeMessage);
        if (!virementRecuService.findById(pk).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        virementRecuService.deleteById(pk);
        return ResponseEntity.noContent().build();
    }
}
