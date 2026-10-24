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
    public Configuration(@NotNull Plugin plugin) {
        super(plugin, Key.builder(Path.of("config.yml"), ConfigFile::new).build());
    }

    public Settings settings() {
        return secondary(SettingsKey.KEY);
    }

    public void saveSettings() {
        save(SettingsKey.KEY);
    }

    @Override
    public List<JacksonModule> additionalModules() {
        List<JacksonModule> modules = new ArrayList<>(super.additionalModules());
        modules.add(new CustomModule());
        return modules;
    }

    /**
     * The key of {@code filter_settings.yml}. Building a key creates its default value, and the default settings need
     * WorldEdit's entity registry. Holding the key in its own class builds it on first use, once WorldEdit is ready,
     * instead of while the plugin is constructed.
     */
    private static final class SettingsKey {
        private static final Key<Settings> KEY = Key.builder(Path.of("filter_settings.yml"), () -> Settings.DEFAULT)
                .name("Filter Settings")
                .rootPackage("de.eldoria.schematicsanitizer.sanitizer")
                .build();
    }
}
