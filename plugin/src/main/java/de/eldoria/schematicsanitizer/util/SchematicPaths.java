/*
 *     SPDX-License-Identifier: LGPL-3.0-or-later
 *
 *     Copyright (C) RainbowDashLabs and Contributor
 */

package de.eldoria.schematicsanitizer.util;

import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.util.Optional;

/**
 * Resolves the paths players type into commands. Every path a command reads or writes has to stay inside the
 * schematic folder, whatever the player typed.
 */
public final class SchematicPaths {
    private SchematicPaths() {
        throw new UnsupportedOperationException("This is a utility class.");
    }

    /**
     * Resolves a path typed by a player against the schematic folder. Spaces may be written as {@code \_}.
     *
     * @param folder the schematic folder
     * @param input  the path as typed
     * @return the normalized absolute path, or empty when the input is not a valid path or leaves the folder
     */
    public static Optional<Path> resolve(Path folder, String input) {
        Path resolved;
        try {
            resolved = folder.resolve(input.replace("\\_", " "));
        } catch (InvalidPathException e) {
            return Optional.empty();
        }
        return isInside(folder, resolved) ? Optional.of(normalized(resolved)) : Optional.empty();
    }

    /**
     * Resolves the name of a new file that is written next to an existing one.
     *
     * @param folder   the schematic folder
     * @param original the file the new one is written next to
     * @param name     the new name as typed
     * @return the normalized absolute path, or empty when the name is not a plain file name in the same folder
     */
    public static Optional<Path> sibling(Path folder, Path original, String name) {
        Path parent = normalized(original).getParent();
        Path resolved;
        try {
            resolved = normalized(parent.resolve(name));
        } catch (InvalidPathException e) {
            return Optional.empty();
        }
        if (!parent.equals(resolved.getParent()) || !isInside(folder, resolved)) return Optional.empty();
        return Optional.of(resolved);
    }

    /**
     * Checks whether a path lies inside a folder once both are normalized, so {@code ..} cannot step out.
     *
     * @param folder the folder
     * @param path   the path to check
     * @return true when the path is the folder or below it
     */
    public static boolean isInside(Path folder, Path path) {
        return normalized(path).startsWith(normalized(folder));
    }

    private static Path normalized(Path path) {
        return path.toAbsolutePath().normalize();
    }
}
