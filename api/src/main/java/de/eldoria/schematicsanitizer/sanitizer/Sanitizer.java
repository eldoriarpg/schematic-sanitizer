/*
 *     SPDX-License-Identifier: LGPL-3.0-or-later
 *
 *     Copyright (C) EldoriaRPG Team and Contributor
 */
package de.eldoria.schematicsanitizer.sanitizer;

import com.sk89q.worldedit.extent.clipboard.Clipboard;
import com.sk89q.worldedit.extent.clipboard.io.BuiltInClipboardFormat;
import com.sk89q.worldedit.function.operation.ForwardExtentCopy;
import com.sk89q.worldedit.function.operation.Operations;
import de.eldoria.schematicsanitizer.sanitizer.report.SanitizerReport;
import de.eldoria.schematicsanitizer.sanitizer.settings.Settings;
import de.eldoria.schematicsanitizer.util.WorldEditUtil;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;

/**
 * The core class to sanitize a schematic.
 * <p>
 * Create a {@link Sanitizer} instance via {@link #create(Path, Settings)}.
 * After that you can either get a report via {@link #check()} or directly {@link #fix()} the schematic by removing invalid blocks and entities.
 */
public class Sanitizer {
    private final Path path;
    private final Settings settings;

    private Sanitizer(Path path, Settings settings) {
        this.path = path;
        this.settings = settings;
    }

    /**
     * Creates a new Sanitizer instance with the given path and settings.
     *
     * @param path     the path to the file to be sanitized
     * @param settings the settings to be used for sanitizing the file
     * @return a new Sanitizer instance
     * @throws IOException if an I/O error occurs while creating the Sanitizer instance
     */
    public static Sanitizer create(Path path, Settings settings) throws IOException {
        if (!path.toFile().exists()) throw new FileNotFoundException("File not found: " + path);
        return new Sanitizer(path, settings);
    }

    /**
     * Executes the sanitization process and returns the resulting SanitizerReport.
     *
     * @return the SanitizerReport containing the results of the sanitization process
     * @throws IOException if an I/O error occurs during the sanitization process
     */
    public SanitizerReport check() throws IOException {
        try (var san = sanitize()) {
            return san.report();
        }
    }

    /**
     * Executes the fix process and returns the resulting SanitizerReport.
     *
     * @return the SanitizerReport containing the results of the fix process
     * @throws IOException if an I/O error occurs during the fix process
     */
    public SanitizerReport fix() throws IOException {
        return fix(path);
    }

    /**
     * Executes the fix process using the specified newName and returns the resulting SanitizerReport.
     *
     * @param newName the name of the fixed file, written in the folder of the original file
     * @return the SanitizerReport containing the results of the fix process
     * @throws IOException              if an I/O error occurs during the fix process
     * @throws IllegalArgumentException if the name is not a plain file name, for example because it contains a folder
     */
    public SanitizerReport fix(String newName) throws IOException {
        Path folder = path.toAbsolutePath().normalize().getParent();
        Path newPath = folder.resolve(fileName(newName)).normalize();
        if (!folder.equals(newPath.getParent())) {
            throw new IllegalArgumentException("Not a plain file name: " + newName);
        }
        return fix(newPath);
    }

    /**
     * The file name {@link #fix(String)} writes to: the name itself when it ends in {@code .schem} or
     * {@code .schematic}, otherwise the name with {@code .schem} added.
     *
     * @param newName the requested name
     * @return the file name that is written
     */
    public static String fileName(String newName) {
        return newName + (newName.endsWith(".schem") || newName.endsWith(".schematic") ? "" : ".schem");
    }

    /**
     * Executes the fix process using the specified newPath and returns the resulting SanitizerReport.
     *
     * @param newPath the new path of the fixed file
     * @return the SanitizerReport containing the results of the fix process
     * @throws IOException if an I/O error occurs during the fix process
     */
    public SanitizerReport fix(Path newPath) throws IOException {
        try (var sanitize = sanitize()) {
            try (var writer = BuiltInClipboardFormat.FAST.getWriter(new FileOutputStream(newPath.toFile()))) {
                writer.write(sanitize);
            }
            return sanitize.report(newPath);
        }
    }

    /**
     * Returns the name of the file without the file extension.
     *
     * @return the name of the file without the file extension.
     */
    public String name() {
        String fileName = path.getFileName().toString();
        int extension = fileName.lastIndexOf('.');
        return extension > 0 ? fileName.substring(0, extension) : fileName;
    }

    private SanitizerExtent sanitize() throws IOException {
        try (Clipboard clipboard = WorldEditUtil.loadSchematic(path)) {
            SanitizerExtent sanitizerExtent = new SanitizerExtent(path, clipboard, settings);
            ForwardExtentCopy copy = new ForwardExtentCopy(
                    clipboard, clipboard.getRegion(), sanitizerExtent, clipboard.getMinimumPoint());
            Operations.completeBlindly(copy);
            return sanitizerExtent;
        }
    }
}
