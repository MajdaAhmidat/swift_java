package com.stage.swift.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;

/**
 * Charge application.properties et expose les répertoires (comme le convertisseur).
 */
public final class PropertiesLoader {

    private static final Properties properties = new Properties();

    static {
        loadProperties();
    }

    private static void loadProperties() {
        try (InputStream input = PropertiesLoader.class.getClassLoader()
                .getResourceAsStream("application.properties")) {
            if (input == null) {
                throw new RuntimeException("Unable to find application.properties");
            }
            properties.load(input);
        } catch (IOException ex) {
            throw new RuntimeException("Error loading properties file", ex);
        }
    }

    public static String getProperty(String key) {
        return properties.getProperty(key);
    }

    /**
     * Retourne la liste des répertoires pour la clé (séparés par des virgules dans application.properties).
     * Ex. input.mx.directory=IN ou EBICS/input/mx,TOS/input/mx
     */
    public static List<String> getDirectories(String key) {
        String dirs = properties.getProperty(key);
        if (dirs == null || dirs.trim().isEmpty()) {
            return null;
        }
        return Arrays.stream(dirs.split(","))
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .collect(Collectors.toList());
    }
}
