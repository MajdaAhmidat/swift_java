package com.stage.swift.utils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;

/**
 * Charge application.properties et expose les répertoires
 */
public final class PropertiesLoader {

    private static final Properties properties = new Properties();
    private static final Object lock = new Object();
    private static volatile long lastLoadedFromFileTs = -1L;
    private static volatile Path externalPropertiesPath;

    static {
        loadProperties();
    }

    private static void loadProperties() {
        synchronized (lock) {
            Path filePath = resolveExternalPropertiesPath();
            try {
                if (filePath != null && Files.exists(filePath)) {
                    long ts = Files.getLastModifiedTime(filePath).toMillis();
                    if (ts == lastLoadedFromFileTs && !properties.isEmpty()) {
                        return;
                    }
                    Properties fresh = new Properties();
                    try (InputStream in = Files.newInputStream(filePath)) {
                        fresh.load(in);
                    }
                    properties.clear();
                    properties.putAll(fresh);
                    lastLoadedFromFileTs = ts;
                    return;
                }
            } catch (IOException ignored) {
                // fallback classpath
            }

            try (InputStream input = PropertiesLoader.class.getClassLoader()
                    .getResourceAsStream("application.properties")) {
                if (input == null) {
                    throw new RuntimeException("Unable to find application.properties");
                }
                Properties fresh = new Properties();
                fresh.load(input);
                properties.clear();
                properties.putAll(fresh);
            } catch (IOException ex) {
                throw new RuntimeException("Error loading properties file", ex);
            }
        }
    }

    public static String getProperty(String key) {
        loadProperties();
        return properties.getProperty(key);
    }


    public static List<String> getDirectories(String key) {
        loadProperties();
        String dirs = properties.getProperty(key);
        if (dirs == null || dirs.trim().isEmpty()) {
            return null;
        }
        return Arrays.stream(dirs.split(","))
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .collect(Collectors.toList());
    }

    private static Path resolveExternalPropertiesPath() {
        if (externalPropertiesPath != null) {
            return externalPropertiesPath;
        }
        String userDir = System.getProperty("user.dir");
        Path fromUserDir = Paths.get(userDir, "src", "main", "resources", "application.properties");
        if (Files.exists(fromUserDir)) {
            externalPropertiesPath = fromUserDir;
            return externalPropertiesPath;
        }
        Path fromSwiftFin = Paths.get(userDir, "swift_fin", "src", "main", "resources", "application.properties");
        if (Files.exists(fromSwiftFin)) {
            externalPropertiesPath = fromSwiftFin;
            return externalPropertiesPath;
        }
        return null;
    }
}
