package com.stage.swift.security;

import com.stage.swift.entity.Utilisateur;
import com.stage.swift.repository.UtilisateurRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UtilisateurRepository utilisateurRepository;

    public CustomUserDetailsService(UtilisateurRepository utilisateurRepository) {
        this.utilisateurRepository = utilisateurRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Utilisateur u = utilisateurRepository.findByLoginWithRole(username)
                .orElseThrow(() -> new UsernameNotFoundException("Utilisateur non trouvé"));

        String roleCode = u.getRole() != null ? u.getRole().getCode() : "ADMIN";

        return User.withUsername(u.getLogin())
                .password(u.getMotdepasse())
                .roles(roleCode)
                .disabled(Boolean.FALSE.equals(u.getActif()))
                .build();
    }
}

