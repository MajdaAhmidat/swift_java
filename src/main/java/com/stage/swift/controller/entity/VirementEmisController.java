package com.stage.swift.controller.entity;

import com.stage.swift.dto.entity.VirementEmisDTO;
import com.stage.swift.entity.VirementEmis;
import com.stage.swift.mapper.EntityMapper;
import com.stage.swift.service.entity.VirementEmisService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/virements-emis")
@CrossOrigin(origins = "*")
public class VirementEmisController {

    private final VirementEmisService virementEmisService;
    private final EntityMapper mapper;

    public VirementEmisController(VirementEmisService virementEmisService, EntityMapper mapper) {
        this.virementEmisService = virementEmisService;
        this.mapper = mapper;
    }

    @GetMapping
    public ResponseEntity<List<VirementEmisDTO>> findAll() {
        List<VirementEmisDTO> list = virementEmisService.findAll().stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(list);
    }

    @GetMapping("/by-id")
    public ResponseEntity<VirementEmisDTO> findById(
            @RequestParam Long idVrtEmis,
            @RequestParam Long idSop,
            @RequestParam Long idStatutStatut,
            @RequestParam Long idAdresseAdresse,
            @RequestParam Long codeBicBic,
            @RequestParam Long codeMsgTypeMessage) {
        VirementEmis.VirementEmisPK pk = new VirementEmis.VirementEmisPK(
                idVrtEmis, idSop, idStatutStatut, idAdresseAdresse, codeBicBic, codeMsgTypeMessage);
        return virementEmisService.findById(pk)
                .map(mapper::toDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<VirementEmisDTO> create(@Valid @RequestBody VirementEmisDTO dto) {
        VirementEmis entity = mapper.toEntity(dto);
        entity = virementEmisService.save(entity);
        return ResponseEntity.status(HttpStatus.CREATED).body(mapper.toDTO(entity));
    }

    @PutMapping
    public ResponseEntity<VirementEmisDTO> update(@Valid @RequestBody VirementEmisDTO dto) {
        VirementEmis.VirementEmisPK pk = new VirementEmis.VirementEmisPK(
                dto.getIdVrtEmis(), dto.getIdSop(), dto.getIdStatutStatut(),
                dto.getIdAdresseAdresse(), dto.getCodeBicBic(), dto.getCodeMsgTypeMessage());
        if (!virementEmisService.findById(pk).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        VirementEmis entity = mapper.toEntity(dto);
        entity = virementEmisService.save(entity);
        return ResponseEntity.ok(mapper.toDTO(entity));
    }

    @DeleteMapping
    public ResponseEntity<Void> delete(
            @RequestParam Long idVrtEmis,
            @RequestParam Long idSop,
            @RequestParam Long idStatutStatut,
            @RequestParam Long idAdresseAdresse,
            @RequestParam Long codeBicBic,
            @RequestParam Long codeMsgTypeMessage) {
        VirementEmis.VirementEmisPK pk = new VirementEmis.VirementEmisPK(
                idVrtEmis, idSop, idStatutStatut, idAdresseAdresse, codeBicBic, codeMsgTypeMessage);
        if (!virementEmisService.findById(pk).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        virementEmisService.deleteById(pk);
        return ResponseEntity.noContent().build();
    }
}
