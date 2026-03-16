package com.stage.swift.service.entity.impl;

import com.stage.swift.entity.Utilisateur;
import com.stage.swift.repository.UtilisateurRepository;
import com.stage.swift.service.entity.UtilisateurService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UtilisateurServiceImpl implements UtilisateurService {

    private final UtilisateurRepository repository;

    public UtilisateurServiceImpl(UtilisateurRepository repository) {
        this.repository = repository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Utilisateur> findAll() {
        return repository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Utilisateur> findById(Utilisateur.UtilisateurPK id) {
        return repository.findById(id);
    }

    @Override
    public Utilisateur save(Utilisateur entity) {
        return repository.save(entity);
    }

    @Override
    public void deleteById(Utilisateur.UtilisateurPK id) {
        repository.deleteById(id);
    }
}
