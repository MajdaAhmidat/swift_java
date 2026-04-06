package com.stage.swift.service.entity.impl;

import com.stage.swift.entity.Utilisateur;
import com.stage.swift.repository.UtilisateurRepository;
import com.stage.swift.service.entity.UtilisateurService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UtilisateurServiceImpl implements UtilisateurService {

    private final UtilisateurRepository repository;
    private final PasswordEncoder passwordEncoder;

    public UtilisateurServiceImpl(UtilisateurRepository repository, PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Utilisateur> findAll() {
        return repository.findAllWithRole();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Utilisateur> findById(Utilisateur.UtilisateurPK id) {
        return repository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Utilisateur> findByIdUser(Integer idUser) {
        return repository.findByIdUser(idUser);
    }

    @Override
    public Utilisateur save(Utilisateur entity) {
        if (entity.getIdUser() == null) {
            if (entity.getMotdepasse() != null && !entity.getMotdepasse().isEmpty()) {
                entity.setMotdepasse(passwordEncoder.encode(entity.getMotdepasse()));
            }
            if (entity.getCreatedAt() == null) {
                entity.setCreatedAt(OffsetDateTime.now());
            }
        }
        return repository.save(entity);
    }

    @Override
    public void deleteById(Utilisateur.UtilisateurPK id) {
        repository.deleteById(id);
    }
}
