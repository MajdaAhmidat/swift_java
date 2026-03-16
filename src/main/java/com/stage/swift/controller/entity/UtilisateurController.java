package com.stage.swift.controller.entity;

import com.stage.swift.dto.entity.UtilisateurDTO;
import com.stage.swift.entity.Utilisateur;
import com.stage.swift.mapper.EntityMapper;
import com.stage.swift.service.entity.UtilisateurService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/utilisateurs")
@CrossOrigin(origins = "*")
public class UtilisateurController {

    private final UtilisateurService utilisateurService;
    private final EntityMapper mapper;

    public UtilisateurController(UtilisateurService utilisateurService, EntityMapper mapper) {
        this.utilisateurService = utilisateurService;
        this.mapper = mapper;
    }

    @GetMapping
    public ResponseEntity<List<UtilisateurDTO>> findAll() {
        List<UtilisateurDTO> list = utilisateurService.findAll().stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(list);
    }

    @GetMapping("/by-id")
    public ResponseEntity<UtilisateurDTO> findById(
            @RequestParam Integer idUser,
            @RequestParam Integer idRoleRole) {
        Utilisateur.UtilisateurPK pk = new Utilisateur.UtilisateurPK(idUser, idRoleRole);
        return utilisateurService.findById(pk)
                .map(mapper::toDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<UtilisateurDTO> create(@Valid @RequestBody UtilisateurDTO dto) {
        Utilisateur entity = mapper.toEntity(dto);
        entity = utilisateurService.save(entity);
        return ResponseEntity.status(HttpStatus.CREATED).body(mapper.toDTO(entity));
    }

    @PutMapping
    public ResponseEntity<UtilisateurDTO> update(@Valid @RequestBody UtilisateurDTO dto) {
        Utilisateur.UtilisateurPK pk = new Utilisateur.UtilisateurPK(dto.getIdUser(), dto.getIdRoleRole());
        if (!utilisateurService.findById(pk).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        Utilisateur entity = mapper.toEntity(dto);
        entity = utilisateurService.save(entity);
        return ResponseEntity.ok(mapper.toDTO(entity));
    }

    @DeleteMapping
    public ResponseEntity<Void> delete(
            @RequestParam Integer idUser,
            @RequestParam Integer idRoleRole) {
        Utilisateur.UtilisateurPK pk = new Utilisateur.UtilisateurPK(idUser, idRoleRole);
        if (!utilisateurService.findById(pk).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        utilisateurService.deleteById(pk);
        return ResponseEntity.noContent().build();
    }
}
