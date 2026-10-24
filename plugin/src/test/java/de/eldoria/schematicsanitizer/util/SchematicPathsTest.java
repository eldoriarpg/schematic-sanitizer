/*
 *     SPDX-License-Identifier: LGPL-3.0-or-later
 *
 *     Copyright (C) RainbowDashLabs and Contributor
 */

package de.eldoria.schematicsanitizer.util;

import org.junit.jupiter.api.Test;

import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class SchematicPathsTest {
    private static final Path FOLDER = Path.of("plugins", "FastAsyncWorldEdit", "schematics").toAbsolutePath();

    @Test
    void resolvesFilesInsideTheFolder() {
        assertEquals(FOLDER.resolve("builds/old castle.schem"),
                SchematicPaths.resolve(FOLDER, "builds/old\\_castle.schem").orElseThrow());
    }

    @Test
    void refusesStepsOutOfTheFolder() {
        assertTrue(SchematicPaths.resolve(FOLDER, "../config.yml").isEmpty());
        assertTrue(SchematicPaths.resolve(FOLDER, "builds/../../config.yml").isEmpty());
    }

    @Test
    void refusesAbsolutePathsOutsideTheFolder() {
        assertTrue(SchematicPaths.resolve(FOLDER, FOLDER.getRoot().resolve("etc/passwd").toString()).isEmpty());
    }

    @Test
    void acceptsPlainSiblingNames() {
        Path original = FOLDER.resolve("builds/castle.schem");
        assertEquals(FOLDER.resolve("builds/clean.schem"),
                SchematicPaths.sibling(FOLDER, original, "clean.schem").orElseThrow());
    }

    @Test
    void refusesSiblingNamesWithFolders() {
        Path original = FOLDER.resolve("builds/castle.schem");
        assertTrue(SchematicPaths.sibling(FOLDER, original, "../clean.schem").isEmpty());
        assertTrue(SchematicPaths.sibling(FOLDER, original, "sub/clean.schem").isEmpty());
        assertTrue(SchematicPaths.sibling(FOLDER, original, "../../../../plugins/evil.jar").isEmpty());
    }
}
