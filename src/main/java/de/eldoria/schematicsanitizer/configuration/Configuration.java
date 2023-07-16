package de.eldoria.schematicsanitizer.configuration;

import com.fasterxml.jackson.databind.Module;
import de.eldoria.eldoutilities.config.ConfigKey;
import de.eldoria.eldoutilities.config.JacksonConfig;
import de.eldoria.schematicsanitizer.configuration.serialization.CustomModule;
import de.eldoria.schematicsanitizer.sanitizer.settings.Settings;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

import java.nio.file.Path;
import java.util.List;

public class Configuration extends JacksonConfig<ConfigFile> {
    public static final ConfigKey<Settings> SETTINGS = ConfigKey.of("Filter Settings", Path.of("filter_settings.yml"),
            Settings.class, () -> Settings.DEFAULT);

    public Configuration(@NotNull Plugin plugin) {
        super(plugin, ConfigKey.defaultConfig(ConfigFile.class, ConfigFile::new));
    }

    public Settings settings() {
        return secondary(SETTINGS);
    }


    @Override
    protected List<Module> additionalModules() {
        return List.of(new CustomModule());
    }
}
