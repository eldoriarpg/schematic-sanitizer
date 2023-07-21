/*
 *     SPDX-License-Identifier: LGPL-3.0-or-later
 *
 *     Copyright (C) RainbowDashLabs and Contributor
 */

package de.eldoria.schematicsanitizer.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;

public final class Completion {
    public static List<String> completeAll(Path base, String search) {
        List<String> completions = new ArrayList<>();
        completions.addAll(Completion.completeDirectories(base, search));
        completions.addAll(Completion.completeFiles(base, search));
        Collections.sort(completions);
        return completions;
    }

    public static List<String> completeDirectories(Path base, String search) {
        return complete(Completion::directoryList, Completion::directoryList, base, search);
    }

    public static List<String> completeFiles(Path base, String search) {
        return complete(Completion::fileList, Completion::fileList, base, search);
    }

    private static List<String> complete(BiFunction<Path, String, List<Path>> map, Function<Path, List<Path>> mapEmpty, Path base, String search) {
        var prefix = base.toAbsolutePath().toString();
        Path searchDir;
        try {
            searchDir = buildPath(base, search);
        } catch (InvalidPathException e) {
            return Collections.emptyList();
        }
        if (search.endsWith("/") || search.isBlank()) {
            return clean(mapEmpty.apply(searchDir), prefix);
        }
        return clean(map.apply(searchDir.getParent(), searchDir.getFileName().toString().toLowerCase()), prefix);
    }

    private static Path buildPath(Path base, String search) throws InvalidPathException {
        search = search.replace("\\_", " ").replaceAll("^([/\\\\]+|[a-zA-Z]:\\\\)", "");
        if (search.contains("..")) throw new InvalidPathException(search, "no stepping out");
        Path resolved = base.resolve(search).toAbsolutePath();
        if (resolved.startsWith(base.toAbsolutePath())) return resolved;
        return base;
    }

    private static List<Path> directoryList(Path path) {
        return list(path, p -> p.toFile().isDirectory());
    }

    private static List<Path> directoryList(Path path, String name) {
        return list(path, p -> p.toFile().isDirectory() && matchPath(p, name));
    }

    private static List<Path> fileList(Path path) {
        return list(path, p -> p.toFile().isFile());
    }

    private static List<Path> fileList(Path path, String name) {
        return list(path, p -> p.toFile().isFile() && matchPath(p, name));
    }

    private static boolean matchPath(Path path, String name) {
        return path.getFileName().toString().toLowerCase().startsWith(name);
    }

    private static List<Path> list(Path path, Predicate<Path> test) {
        try (var files = Files.list(path)) {
            return files.filter(test)
                    .toList();
        } catch (IOException e) {
            return Collections.emptyList();
        }
    }

    private static List<String> clean(Collection<Path> paths, String clean) {
        return paths.stream()
                .map(p -> p.toFile().isDirectory() ? p + "/" : p.toString())
                .map(p -> p.replace("\\", "/"))
                .map(p -> p.replace(clean, "").replace(" ", "\\_").replaceAll("^/", ""))
                .filter(s -> !s.isBlank())
                .sorted()
                .toList();
    }
}
