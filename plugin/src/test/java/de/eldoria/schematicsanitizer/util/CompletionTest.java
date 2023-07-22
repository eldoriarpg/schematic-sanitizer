/*
 *     SPDX-License-Identifier: LGPL-3.0-or-later
 *
 *     Copyright (C) RainbowDashLabs and Contributor
 */

package de.eldoria.schematicsanitizer.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;
import java.util.List;

class CompletionTest {
    private static final Path root = Path.of("src", "main", "java", "de", "eldoria", "schematicsanitizer");
    private static final Path util = Path.of("src", "main", "java", "de", "eldoria", "schematicsanitizer", "util");

    @Test
    void completeEmptyDirectory() {
        List<String> strings = Completion.completeDirectories(root, "");
        Assertions.assertTrue(strings.contains("command/"));
        Assertions.assertEquals(4, strings.size());
    }

    @Test
    void completeEmptyDirectorySlash() {
        List<String> strings = Completion.completeDirectories(root, "/");
        Assertions.assertTrue(strings.contains("command/"));
        Assertions.assertEquals(4, strings.size());
    }

    @Test
    void completeSearchDirectory() {
        List<String> strings = Completion.completeDirectories(root, "com");
        Assertions.assertTrue(strings.contains("command/"));
        Assertions.assertEquals(1, strings.size());
    }

    @Test
    void completeSearchSubDirectory() {
        List<String> strings = Completion.completeDirectories(root, "rendering/");
        Assertions.assertTrue(strings.contains("rendering/entities/"));
        Assertions.assertEquals(2, strings.size());
    }

    @Test
    void completeSearchSubDirectorySearch() {
        List<String> strings = Completion.completeDirectories(root, "rendering/En");
        Assertions.assertTrue(strings.contains("rendering/entities/"));
        Assertions.assertEquals(1, strings.size());
    }

    @Test
    void completeSearchFile() {
        List<String> strings = Completion.completeFiles(util, "Com");
        Assertions.assertTrue(strings.contains("Completion.java"));
        Assertions.assertEquals(1, strings.size());
    }

    @Test
    void completeEmptyFile() {
        List<String> strings = Completion.completeFiles(util, "");
        Assertions.assertTrue(strings.contains("Completion.java"));
        Assertions.assertEquals(4, strings.size());
    }

    @Test
    void completeEmptyFileSlash() {
        List<String> strings = Completion.completeFiles(util, "/");
        Assertions.assertTrue(strings.contains("Completion.java"));
        Assertions.assertEquals(4, strings.size());
    }

    @Test
    void completeSearchSubDirectoryFiles() {
        List<String> strings = Completion.completeFiles(root, "rendering/entities/");
        Assertions.assertTrue(strings.contains("rendering/entities/ComponentEntityRenderer.java"));
        Assertions.assertEquals(5, strings.size());
    }

    @Test
    void completeSearchSubDirectoryFileSearch() {
        List<String> strings = Completion.completeFiles(root, "rendering/rep");
        Assertions.assertTrue(strings.contains("rendering/ReportRenderer.java"));
        Assertions.assertEquals(1, strings.size());
    }
}
