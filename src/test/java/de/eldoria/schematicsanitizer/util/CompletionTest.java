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

import static org.junit.jupiter.api.Assertions.*;

class CompletionTest {

    @Test
    void completeEmptyDirectory() {
        Path base = Path.of("src", "main", "java", "de", "eldoria", "schematicsanitizer");
        List<String> strings = Completion.completeDirectories(base, "");
        Assertions.assertTrue(strings.contains("command/"));
        Assertions.assertEquals(4, strings.size());
    }
    @Test
    void completeSearchDirectory() {
        Path base = Path.of("src", "main", "java", "de", "eldoria", "schematicsanitizer");
        List<String> strings = Completion.completeDirectories(base, "com");
        Assertions.assertTrue(strings.contains("command/"));
        Assertions.assertEquals(1, strings.size());
    }
    @Test
    void completeSearchSubDirectory() {
        Path base = Path.of("src", "main", "java", "de", "eldoria", "schematicsanitizer");
        List<String> strings = Completion.completeDirectories(base, "sanitizer/");
        Assertions.assertTrue(strings.contains("sanitizer/report/"));
        Assertions.assertEquals(4, strings.size());
    }
    @Test
    void completeSearchSubDirectorySearch() {
        Path base = Path.of("src", "main", "java", "de", "eldoria", "schematicsanitizer");
        List<String> strings = Completion.completeDirectories(base, "sanitizer/Fi");
        Assertions.assertTrue(strings.contains("sanitizer/filter/"));
        Assertions.assertEquals(1, strings.size());
    }
    @Test
    void completeSearchFile() {
        Path base = Path.of("src", "main", "java", "de", "eldoria", "schematicsanitizer", "util");
        List<String> strings = Completion.completeFiles(base, "Com");
        Assertions.assertTrue(strings.contains("Completion.java"));
        Assertions.assertEquals(1, strings.size());
    }
    @Test
    void completeEmptyFile() {
        Path base = Path.of("src", "main", "java", "de", "eldoria", "schematicsanitizer", "util");
        List<String> strings = Completion.completeFiles(base, "");
        Assertions.assertTrue(strings.contains("Completion.java"));
        Assertions.assertEquals(4, strings.size());
    }

    @Test
    void completeSearchSubDirectoryFiles() {
        Path base = Path.of("src", "main", "java", "de", "eldoria", "schematicsanitizer");
        List<String> strings = Completion.completeFiles(base, "sanitizer/");
        Assertions.assertTrue(strings.contains("sanitizer/Sanitizer.java"));
        Assertions.assertEquals(3, strings.size());
    }

    void completeSearchSubDirectoryFileSearch() {
        Path base = Path.of("src", "main", "java", "de", "eldoria", "schematicsanitizer");
        List<String> strings = Completion.completeFiles(base, "sanitizer/san");
        Assertions.assertTrue(strings.contains("sanitizer/Sanitizer.java"));
        Assertions.assertEquals(2, strings.size());
    }

    @Test
    void completeFiles() {
    }
}
