package com.stage.swift.controller.entity;

import com.stage.swift.dto.entity.VirementRecuHistoDTO;
import com.stage.swift.entity.VirementRecuHisto;
import com.stage.swift.mapper.EntityMapper;
import com.stage.swift.service.entity.VirementRecuHistoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

/** REST Controller pour VIREMENT_RECU_HISTO (historique des virements reçus). */
@RestController
@RequestMapping("/api/virements-recu-histo")
@CrossOrigin(origins = "*")
public class VirementRecuHistoController {

    private final VirementRecuHistoService virementRecuHistoService;
    private final EntityMapper mapper;

    public VirementRecuHistoController(VirementRecuHistoService virementRecuHistoService, EntityMapper mapper) {
        this.virementRecuHistoService = virementRecuHistoService;
        this.mapper = mapper;
    }

    @GetMapping
    public ResponseEntity<List<VirementRecuHistoDTO>> findAll() {
        List<VirementRecuHistoDTO> list = virementRecuHistoService.findAll().stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(list);
    }

    @GetMapping("/by-id")
    public ResponseEntity<VirementRecuHistoDTO> findById(
            @RequestParam Long idVrtRecuHisto,
            @RequestParam Long idVrtRecuVirementRecu,
            @RequestParam Long idStatutStatutVirementRecu,
            @RequestParam Long idAdresseAdresseVirementRecu,
            @RequestParam Long idSopVirementRecu,
            @RequestParam Long codeBicBicVirementRecu,
            @RequestParam Long codeMsgTypeMessageVirementRecu) {
        VirementRecuHisto.VirementRecuHistoPK pk = new VirementRecuHisto.VirementRecuHistoPK(
                idVrtRecuHisto, idVrtRecuVirementRecu, idStatutStatutVirementRecu, idAdresseAdresseVirementRecu,
                idSopVirementRecu, codeBicBicVirementRecu, codeMsgTypeMessageVirementRecu);
        return virementRecuHistoService.findById(pk)
                .map(mapper::toDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<VirementRecuHistoDTO> create(@Valid @RequestBody VirementRecuHistoDTO dto) {
        VirementRecuHisto entity = mapper.toEntity(dto);
        entity = virementRecuHistoService.save(entity);
        return ResponseEntity.status(HttpStatus.CREATED).body(mapper.toDTO(entity));
    }

    @PutMapping
    public ResponseEntity<VirementRecuHistoDTO> update(@Valid @RequestBody VirementRecuHistoDTO dto) {
        VirementRecuHisto.VirementRecuHistoPK pk = new VirementRecuHisto.VirementRecuHistoPK(
                dto.getIdVrtRecuHisto(), dto.getIdVrtRecuVirementRecu(), dto.getIdStatutStatutVirementRecu(),
                dto.getIdAdresseAdresseVirementRecu(), dto.getIdSopVirementRecu(), dto.getCodeBicBicVirementRecu(),
                dto.getCodeMsgTypeMessageVirementRecu());
        if (!virementRecuHistoService.findById(pk).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        VirementRecuHisto entity = mapper.toEntity(dto);
        entity = virementRecuHistoService.save(entity);
        return ResponseEntity.ok(mapper.toDTO(entity));
    }

    @DeleteMapping
    public ResponseEntity<Void> delete(
            @RequestParam Long idVrtRecuHisto,
            @RequestParam Long idVrtRecuVirementRecu,
            @RequestParam Long idStatutStatutVirementRecu,
            @RequestParam Long idAdresseAdresseVirementRecu,
            @RequestParam Long idSopVirementRecu,
            @RequestParam Long codeBicBicVirementRecu,
            @RequestParam Long codeMsgTypeMessageVirementRecu) {
        VirementRecuHisto.VirementRecuHistoPK pk = new VirementRecuHisto.VirementRecuHistoPK(
                idVrtRecuHisto, idVrtRecuVirementRecu, idStatutStatutVirementRecu, idAdresseAdresseVirementRecu,
                idSopVirementRecu, codeBicBicVirementRecu, codeMsgTypeMessageVirementRecu);
        if (!virementRecuHistoService.findById(pk).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        virementRecuHistoService.deleteById(pk);
        return ResponseEntity.noContent().build();
    }
}
