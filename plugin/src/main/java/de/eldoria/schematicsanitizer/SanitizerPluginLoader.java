/*
 *     SPDX-License-Identifier: AGPL-3.0-only
 *
 *     Copyright (C) EldoriaRPG Team and Contributor
 */
package de.eldoria.schematicsanitizer;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import io.papermc.paper.plugin.loader.PluginClasspathBuilder;
import io.papermc.paper.plugin.loader.PluginLoader;
import io.papermc.paper.plugin.loader.library.impl.MavenLibraryResolver;
import org.eclipse.aether.artifact.DefaultArtifact;
import org.eclipse.aether.graph.Dependency;
import org.eclipse.aether.repository.RemoteRepository;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UncheckedIOException;
import java.nio.charset.StandardCharsets;

/**
 * Lets Paper download the libraries of the plugin before it is loaded.
 * <p>
 * The libraries are listed in {@code paper-libraries.json}, written by plugin-yml from the {@code paperLibrary}
 * dependencies of the build. They are resolved from Paper's Maven Central mirror first and from EldoNexus after that:
 * eldo-util is only published in its releases repository, and its proxies cover what the mirror lacks.
 */
public class SanitizerPluginLoader implements PluginLoader {
    private static final String LIBRARIES = "/paper-libraries.json";
    private static final String ELDONEXUS_RELEASES = "https://eldonexus.de/repository/maven-releases/";
    private static final String ELDONEXUS_PROXIES = "https://eldonexus.de/repository/maven-proxies/";

    @Override
    public void classloader(@NotNull PluginClasspathBuilder classpathBuilder) {
        MavenLibraryResolver resolver = new MavenLibraryResolver();
        resolver.addRepository(repository("central", MavenLibraryResolver.MAVEN_CENTRAL_DEFAULT_MIRROR));
        resolver.addRepository(repository("eldonexus-releases", ELDONEXUS_RELEASES));
        resolver.addRepository(repository("eldonexus-proxies", ELDONEXUS_PROXIES));
        libraries()
                .get("dependencies")
                .getAsJsonArray()
                .forEach(library ->
                        resolver.addDependency(new Dependency(new DefaultArtifact(library.getAsString()), null)));
        classpathBuilder.addLibrary(resolver);
    }

    private static RemoteRepository repository(String id, String url) {
        return new RemoteRepository.Builder(id, "default", url).build();
    }

    private static JsonObject libraries() {
        try (InputStream in = SanitizerPluginLoader.class.getResourceAsStream(LIBRARIES)) {
            if (in == null) throw new IllegalStateException(LIBRARIES + " is missing in the plugin jar");
            return new Gson().fromJson(new InputStreamReader(in, StandardCharsets.UTF_8), JsonObject.class);
        } catch (IOException e) {
            throw new UncheckedIOException("Could not read " + LIBRARIES, e);
        }
    }
}
