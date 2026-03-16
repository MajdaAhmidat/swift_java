package com.stage.swift.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import static com.stage.swift.utils.Constantes.LINE_SEPARATOR;
import static com.stage.swift.utils.Constantes.IN_SAA_DIR;
import static com.stage.swift.utils.Constantes.OUT_SAA_DIR;
import static com.stage.swift.utils.Constantes.OUT_SOP_DIR;
import static com.stage.swift.utils.Constantes.SAVE_SAA_DIR;
import static com.stage.swift.utils.Constantes.ERROR_SAA_DIR;
import static com.stage.swift.utils.Constantes.SAVE_SOP_DIR;
import static com.stage.swift.utils.Constantes.ERROR_SOP_DIR;

/**
 * Utilitaires fichiers : lecture, déplacement vers SAVE ou REJET.
 * Utilisé par DocumentProcessorService uniquement.
 */
public final class FileUtils {

    private FileUtils() {
    }

    public static String fileToString(File file) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line).append(LINE_SEPARATOR);
            }
            return sb.toString();
        }
    }

    public static void moveToSave(String filePath) throws IOException {
        Path sourcePath = Paths.get(filePath);
        Path targetPath = buildTargetPath(filePath, true);
        Files.createDirectories(targetPath.getParent());
        Files.move(sourcePath, targetPath, StandardCopyOption.REPLACE_EXISTING);
    }

    public static void moveToRejet(String filePath) throws IOException {
        Path sourcePath = Paths.get(filePath);
        Path targetPath = buildTargetPath(filePath, false);
        Files.createDirectories(targetPath.getParent());
        Files.move(sourcePath, targetPath, StandardCopyOption.REPLACE_EXISTING);
    }

    /**
     * Déplace le fichier vers le répertoire cible (ex. IN_ACK après traitement SOP).
     * Crée le répertoire s'il n'existe pas.
     */
    public static void moveToDirectory(String filePath, String targetDirPath) throws IOException {
        Path sourcePath = Paths.get(filePath);
        Path targetDir = Paths.get(targetDirPath);
        Files.createDirectories(targetDir);
        Path targetPath = targetDir.resolve(sourcePath.getFileName());
        Files.move(sourcePath, targetPath, StandardCopyOption.REPLACE_EXISTING);
    }

    /**
     * Déplace le fichier vers le répertoire cible en changeant l'extension (ex. .xml → .ack pour IN_ACK).
     */
    public static void moveToDirectoryWithExtension(String filePath, String targetDirPath, String newExtension) throws IOException {
        Path sourcePath = Paths.get(filePath);
        Path targetDir = Paths.get(targetDirPath);
        Files.createDirectories(targetDir);
        String baseName = sourcePath.getFileName().toString();
        int lastDot = baseName.lastIndexOf('.');
        if (lastDot > 0) baseName = baseName.substring(0, lastDot);
        if (newExtension != null && !newExtension.startsWith(".")) newExtension = "." + newExtension;
        Path targetPath = targetDir.resolve(baseName + (newExtension != null ? newExtension : ""));
        Files.move(sourcePath, targetPath, StandardCopyOption.REPLACE_EXISTING);
    }

    /**
     * Construit le chemin cible pour un déplacement en SAVE_* ou ERREUR_* selon le type de dossier :
     * - IN_SOP / OUT_SOP → SAVE_SOP ou ERREUR_SOP
     * - IN_SAA / OUT_SAA → SAVE_SAA ou ERREUR_SAA
     */
    private static Path buildTargetPath(String filePath, boolean success) {
        Path sourcePath = Paths.get(filePath);
        Path parent = sourcePath.getParent();
        if (parent == null) return sourcePath;
        String parentName = parent.getFileName() != null ? parent.getFileName().toString().toUpperCase() : "";
        boolean isSaa = parentName.contains(IN_SAA_DIR) || parentName.contains(OUT_SAA_DIR);
        boolean isSop = parentName.contains(OUT_SOP_DIR);
        String targetFolderName;
        if (isSaa) {
            targetFolderName = success ? SAVE_SAA_DIR : ERROR_SAA_DIR;
        } else if (isSop) {
            targetFolderName = success ? SAVE_SOP_DIR : ERROR_SOP_DIR;
        } else {
            targetFolderName = success ? SAVE_SOP_DIR : ERROR_SOP_DIR;
        }
        return parent.resolve(targetFolderName).resolve(sourcePath.getFileName());
    }
}
