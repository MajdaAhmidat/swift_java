package com.stage.swift.service;

import com.stage.swift.entity.Role;
import com.stage.swift.entity.Utilisateur;
import com.stage.swift.repository.RoleRepository;
import com.stage.swift.repository.UtilisateurRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UtilisateurService {

    private final UtilisateurRepository utilisateurRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Value("${app.bootstrap.admin.login:}")
    private String bootstrapAdminLogin;

    @Value("${app.bootstrap.admin.password:root}")
    private String bootstrapAdminPassword;

    public UtilisateurService(UtilisateurRepository utilisateurRepository,
                              RoleRepository roleRepository,
                              PasswordEncoder passwordEncoder) {
        this.utilisateurRepository = utilisateurRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Crée/actualise l'admin initial si configuré (app.bootstrap.admin.login dans application.properties).
     * Aucun email en dur dans le code.
     */
    public void ensureAdminExists() {
        if (bootstrapAdminLogin == null || bootstrapAdminLogin.trim().isEmpty()) {
            return;
        }
        String adminLogin = bootstrapAdminLogin.trim();

        Utilisateur admin = utilisateurRepository.findByLogin(adminLogin).orElse(null);

        // chercher le rôle ADMIN, ou en créer un minimal si absent
        Role roleAdmin = roleRepository.findAll().stream()
                .filter(r -> "ADMIN".equalsIgnoreCase(r.getCode()))
                .findFirst()
                .orElseGet(() -> {
                    Role r = new Role();
                    r.setCode("ADMIN");
                    r.setLabel("Administrateur");
                    return roleRepository.save(r);
                });

        if (admin == null) {
            admin = new Utilisateur();
            admin.setLogin(adminLogin);
            admin.setActif(true);
            admin.setIdRoleRole(roleAdmin.getIdRole());
        } else {
            admin.setIdRoleRole(roleAdmin.getIdRole());
        }

        admin.setMotdepasse(passwordEncoder.encode(bootstrapAdminPassword));
        utilisateurRepository.save(admin);
    }

    /**
     * Encode en BCrypt les mots de passe qui semblent en clair.
     * Heuristique simple : si le champ ne commence pas par "$2a$" on le considère comme non encodé.
     */
    public void encodeExistingPasswordsIfNeeded() {
        List<Utilisateur> all = utilisateurRepository.findAll();
        for (Utilisateur u : all) {
            String current = u.getMotdepasse();
            if (current != null && !current.startsWith("$2a$")) {
                u.setMotdepasse(passwordEncoder.encode(current));
                utilisateurRepository.save(u);
            }
        }
    }
}

