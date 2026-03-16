package com.stage.swift;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class SwiftApplication {

    /** Dossier IN obligatoire : path pour les fichiers XML d'entrée (batch MX). */
    public static final String PATH_IN = "IN";

    public static void main(String[] args) {
        SpringApplication.run(SwiftApplication.class, args);
    }
}
