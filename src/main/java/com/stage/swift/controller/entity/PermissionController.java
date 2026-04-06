package com.stage.swift.controller.entity;

import com.stage.swift.dto.entity.PermissionMatrixDTO;
import com.stage.swift.entity.UtilisateurPermission;
import com.stage.swift.repository.UtilisateurPermissionRepository;
import com.stage.swift.repository.UtilisateurRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/permissions")
@CrossOrigin(origins = "*")
public class PermissionController {

    private final UtilisateurPermissionRepository utilisateurPermissionRepository;
    private final UtilisateurRepository utilisateurRepository;

    public PermissionController(UtilisateurPermissionRepository utilisateurPermissionRepository,
                                UtilisateurRepository utilisateurRepository) {
        this.utilisateurPermissionRepository = utilisateurPermissionRepository;
        this.utilisateurRepository = utilisateurRepository;
    }

    @GetMapping("/user/{idUser}")
    public ResponseEntity<List<PermissionMatrixDTO>> getByUser(@PathVariable Integer idUser) {
        if (!utilisateurRepository.findByIdUser(idUser).isPresent()) {
            return ResponseEntity.notFound().build();
        }

        List<UtilisateurPermission> rows = utilisateurPermissionRepository.findByIdUser(idUser);
        if (rows.isEmpty()) {
            return ResponseEntity.ok(Collections.emptyList());
        }
        return ResponseEntity.ok(rows.stream().map(this::toDto).collect(Collectors.toList()));
    }

    @PutMapping("/user/{idUser}")
    @Transactional
    public ResponseEntity<List<PermissionMatrixDTO>> saveByUser(
            @PathVariable Integer idUser,
            @Valid @RequestBody List<PermissionMatrixDTO> matrix) {

        if (!utilisateurRepository.findByIdUser(idUser).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        if (matrix == null || matrix.isEmpty()) {
            utilisateurPermissionRepository.deleteByIdUser(idUser);
            return ResponseEntity.ok(Collections.emptyList());
        }

        List<UtilisateurPermission> existingRows = utilisateurPermissionRepository.findByIdUser(idUser);
        Map<String, UtilisateurPermission> existingByModule = existingRows.stream()
                .filter(p -> p.getModule() != null)
                .collect(Collectors.toMap(
                        p -> normalizeModule(p.getModule()),
                        p -> p,
                        (a, b) -> a
                ));

        Set<String> targetModules = new LinkedHashSet<>();
        for (PermissionMatrixDTO row : matrix) {
            if (row == null || row.getModule() == null || row.getModule().trim().isEmpty()) {
                continue;
            }
            String normalizedModule = normalizeModule(row.getModule());
            UtilisateurPermission target = existingByModule.get(normalizedModule);
            if (target == null) {
                target = new UtilisateurPermission();
                target.setIdUser(idUser);
                target.setModule(normalizedModule);
            }
            target.setLire(Boolean.TRUE.equals(row.getLire()));
            target.setCreer(Boolean.TRUE.equals(row.getCreer()));
            target.setModifier(Boolean.TRUE.equals(row.getModifier()));
            target.setSupprimer(Boolean.TRUE.equals(row.getSupprimer()));
            target.setValider(Boolean.TRUE.equals(row.getValider()));
            utilisateurPermissionRepository.save(target);
            targetModules.add(normalizedModule);
        }

        for (UtilisateurPermission existing : existingRows) {
            String module = existing.getModule();
            if (module == null || targetModules.contains(normalizeModule(module))) {
                continue;
            }
            utilisateurPermissionRepository.deleteById(existing.getId());
        }

        return getByUser(idUser);
    }

    private PermissionMatrixDTO toDto(UtilisateurPermission p) {
        PermissionMatrixDTO dto = new PermissionMatrixDTO();
        dto.setModule(p.getModule());
        dto.setLire(Boolean.TRUE.equals(p.getLire()));
        dto.setCreer(Boolean.TRUE.equals(p.getCreer()));
        dto.setModifier(Boolean.TRUE.equals(p.getModifier()));
        dto.setSupprimer(Boolean.TRUE.equals(p.getSupprimer()));
        dto.setValider(Boolean.TRUE.equals(p.getValider()));
        return dto;
    }

    private String normalizeModule(String module) {
        return module.trim().toLowerCase().replace('-', '_').replace(' ', '_');
    }
}
