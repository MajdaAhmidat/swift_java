package com.stage.swift.web;

import com.stage.swift.entity.Utilisateur;
import com.stage.swift.repository.UtilisateurRepository;
import com.stage.swift.security.JwtUtil;
import com.stage.swift.web.dto.LoginRequest;
import com.stage.swift.web.dto.UpdateMeRequest;
import com.stage.swift.web.dto.LoginResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/auth")
public class AuthController {

    private final JwtUtil jwtUtil;
    private final UtilisateurRepository utilisateurRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthController(JwtUtil jwtUtil,
                          UtilisateurRepository utilisateurRepository,
                          PasswordEncoder passwordEncoder) {
        this.jwtUtil = jwtUtil;
        this.utilisateurRepository = utilisateurRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
        Utilisateur u = utilisateurRepository.findByLoginWithRole(request.getEmail())
                .orElse(null);

        if (u == null || !passwordEncoder.matches(request.getPassword(), u.getMotdepasse())) {
            return ResponseEntity.status(403).build();
        }
        if (Boolean.FALSE.equals(u.getActif())) {
            return ResponseEntity.status(403).build();
        }

        String roleCode = u.getRole() != null ? u.getRole().getCode() : "ADMIN";
        String token = jwtUtil.generateToken(u.getLogin(), roleCode);

        LoginResponse response = new LoginResponse(
                token,
                u.getLogin(),
                roleCode,
                u.getNom(),
                u.getPrenom()
        );
        return ResponseEntity.ok(response);
    }

    @GetMapping("/me")
    public ResponseEntity<Map<String, Object>> me(@AuthenticationPrincipal UserDetails user) {
        if (user == null || user.getUsername() == null) {
            return ResponseEntity.status(401).build();
        }
        Utilisateur u = utilisateurRepository.findByLoginWithRole(user.getUsername()).orElse(null);
        if (u == null) {
            return ResponseEntity.status(404).build();
        }
        String roleCode = u.getRole() != null ? u.getRole().getCode() : "ADMIN";

        Map<String, Object> body = new HashMap<>();
        body.put("idUser", u.getIdUser());
        body.put("email", u.getLogin());
        body.put("roleCode", roleCode);
        body.put("nom", u.getNom() != null ? u.getNom() : "");
        body.put("prenom", u.getPrenom() != null ? u.getPrenom() : "");
        body.put("actif", u.getActif() != null ? u.getActif() : true);
        if (u.getCreatedAt() != null) {
            body.put("createdAt", u.getCreatedAt().format(DateTimeFormatter.ISO_OFFSET_DATE_TIME));
        } else {
            body.put("createdAt", null);
        }
        return ResponseEntity.ok(body);
    }

    @PatchMapping("/me")
    public ResponseEntity<Void> updateMe(@AuthenticationPrincipal UserDetails user,
                                          @RequestBody UpdateMeRequest req) {
        if (user == null || user.getUsername() == null) {
            return ResponseEntity.status(401).build();
        }
        Utilisateur u = utilisateurRepository.findByLogin(user.getUsername()).orElse(null);
        if (u == null) {
            return ResponseEntity.status(404).build();
        }

        boolean updated = false;

        if (req.getNom() != null) {
            u.setNom(req.getNom().trim().isEmpty() ? null : req.getNom().trim());
            updated = true;
        }
        if (req.getPrenom() != null) {
            u.setPrenom(req.getPrenom().trim().isEmpty() ? null : req.getPrenom().trim());
            updated = true;
        }

        if (req.getNewPassword() != null && !req.getNewPassword().trim().isEmpty()) {
            if (req.getCurrentPassword() != null && !passwordEncoder.matches(req.getCurrentPassword(), u.getMotdepasse())) {
                return ResponseEntity.status(403).build();
            }
            u.setMotdepasse(passwordEncoder.encode(req.getNewPassword().trim()));
            updated = true;
        }

        if (updated) {
            utilisateurRepository.save(u);
        }
        return ResponseEntity.noContent().build();
    }
}

