package com.stage.swift;

import com.stage.swift.service.UtilisateurService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableScheduling
public class SwiftApplication {

    public static final String PATH_IN = "IN_SOP";

    public static void main(String[] args) {
        SpringApplication.run(SwiftApplication.class, args);
    }

    @Bean
    CommandLineRunner initUsers(UtilisateurService utilisateurService) {
        return args -> {
            utilisateurService.ensureAdminExists();
            utilisateurService.encodeExistingPasswordsIfNeeded();
        };
    }
}
