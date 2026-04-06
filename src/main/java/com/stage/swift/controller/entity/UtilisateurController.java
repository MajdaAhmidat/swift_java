package com.stage.swift.controller.entity;

import com.stage.swift.dto.entity.UtilisateurDTO;
import com.stage.swift.entity.Utilisateur;
import com.stage.swift.mapper.EntityMapper;
import com.stage.swift.repository.UtilisateurRepository;
import com.stage.swift.service.entity.UtilisateurService;
import org.springframework.security.crypto.password.PasswordEncoder;
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
    private final UtilisateurRepository utilisateurRepository;
    private final EntityMapper mapper;
    private final PasswordEncoder passwordEncoder;

    public UtilisateurController(UtilisateurService utilisateurService,
                                 UtilisateurRepository utilisateurRepository,
                                 EntityMapper mapper,
                                 PasswordEncoder passwordEncoder) {
        this.utilisateurService = utilisateurService;
        this.utilisateurRepository = utilisateurRepository;
        this.mapper = mapper;
        this.passwordEncoder = passwordEncoder;
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

    @GetMapping("/{idUser}")
    public ResponseEntity<UtilisateurDTO> findByIdUser(@PathVariable Integer idUser) {
        return utilisateurService.findByIdUser(idUser)
                .map(mapper::toDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody UtilisateurDTO dto) {
        if (dto.getLogin() == null || dto.getLogin().trim().isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        if (utilisateurRepository.existsByLogin(dto.getLogin().trim())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Login/email déjà utilisé.");
        }
        Utilisateur entity = mapper.toEntity(dto);
        entity.setIdUser(null);
        entity = utilisateurService.save(entity);
        return ResponseEntity.status(HttpStatus.CREATED).body(mapper.toDTO(entity));
    }

    @PutMapping
    public ResponseEntity<?> update(@Valid @RequestBody UtilisateurDTO dto) {
        if (dto.getIdUser() == null) {
            return ResponseEntity.badRequest().build();
        }
        Utilisateur existing = utilisateurService.findByIdUser(dto.getIdUser()).orElse(null);
        if (existing == null) {
            return ResponseEntity.notFound().build();
        }

        // Contrôle unicité email/login en update (hors utilisateur courant)
        String newLogin = dto.getLogin() != null ? dto.getLogin().trim() : null;
        if (newLogin == null || newLogin.isEmpty()) {
            return ResponseEntity.badRequest().body("Login/email est obligatoire.");
        }
        Utilisateur byLogin = utilisateurRepository.findByLogin(newLogin).orElse(null);
        if (byLogin != null && !byLogin.getIdUser().equals(existing.getIdUser())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Login/email déjà utilisé.");
        }

        // Met à jour l'entité existante au lieu de recréer une nouvelle instance.
        // Avec la PK composite (id_user + id_role), recréer peut conduire à un INSERT inattendu.
        existing.setLogin(newLogin);
        // IMPORTANT: id_role fait partie de la clé primaire composite.
        // Le changer pendant un update JPA peut être interprété comme une nouvelle ligne (INSERT).
        // Ici on garde la clé existante pour garantir un vrai update de l'utilisateur.
        existing.setIdRoleRole(existing.getIdRoleRole());
        existing.setNom(dto.getNom());
        existing.setPrenom(dto.getPrenom());
        existing.setTelephone(dto.getTelephone());
        existing.setDepartement(dto.getDepartement());
        existing.setPoste(dto.getPoste());
        existing.setStatut(dto.getStatut());
        existing.setActif(dto.getActif() != null ? dto.getActif() : existing.getActif());

        if (dto.getMotdepasse() == null || dto.getMotdepasse().isEmpty()) {
            existing.setMotdepasse(existing.getMotdepasse());
        } else {
            existing.setMotdepasse(passwordEncoder.encode(dto.getMotdepasse()));
        }

        utilisateurService.save(existing);
        // Recharge avec le rôle (join fetch) pour éviter LazyInitializationException dans le mapper
        Utilisateur reloaded = utilisateurService.findByIdUser(existing.getIdUser()).orElse(existing);
        return ResponseEntity.ok(mapper.toDTO(reloaded));
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
