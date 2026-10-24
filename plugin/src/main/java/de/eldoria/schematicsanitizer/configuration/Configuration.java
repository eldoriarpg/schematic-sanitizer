/*
 *     SPDX-License-Identifier: AGPL-3.0-only
 *
 *     Copyright (C) EldoriaRPG Team and Contributor
 */
package de.eldoria.schematicsanitizer.configuration;

import de.eldoria.eldoutilities.config.JacksonConfig;
import de.eldoria.schematicsanitizer.configuration.serialization.CustomModule;
import de.eldoria.schematicsanitizer.sanitizer.settings.Settings;
import dev.chojo.ocular.key.Key;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import tools.jackson.databind.JacksonModule;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class Configuration extends JacksonConfig<ConfigFile> {
    public static final Key<Settings> SETTINGS = Key.builder(Path.of("filter_settings.yml"), () -> Settings.DEFAULT)
            .name("Filter Settings")
            .rootPackage("de.eldoria.schematicsanitizer.sanitizer")
            .build();

    public Configuration(@NotNull Plugin plugin) {
        super(plugin, Key.builder(Path.of("config.yml"), ConfigFile::new).build());
    }

    public Settings settings() {
        return secondary(SETTINGS);
    }

    @Override
    public List<JacksonModule> additionalModules() {
        List<JacksonModule> modules = new ArrayList<>(super.additionalModules());
        modules.add(new CustomModule());
        return modules;
    }
}
