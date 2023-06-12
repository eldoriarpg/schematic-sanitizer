package de.eldoria.schematicsanitizer;

import de.eldoria.eldoutilities.config.ConfigKey;
import de.eldoria.eldoutilities.config.JacksonConfig;
import de.eldoria.eldoutilities.config.template.PluginBaseConfiguration;
import de.eldoria.eldoutilities.messages.MessageSender;
import de.eldoria.eldoutilities.plugin.EldoPlugin;
import de.eldoria.schematicsanitizer.command.SchematicClean;
import de.eldoria.schematicsanitizer.configuration.ConfigFile;
import de.eldoria.schematicsanitizer.configuration.Configuration;
import net.kyori.adventure.text.format.TextColor;

import java.util.logging.Level;

@SuppressWarnings("unused")
public class SanitizerPlugin extends EldoPlugin {
    private final Configuration configuration;

    public SanitizerPlugin() {
        configuration = new Configuration(this);
    }

    @Override
    public Level getLogLevel() {
        return configuration.secondary(PluginBaseConfiguration.KEY).logLevel();
    }

    @Override
    public void onPluginEnable() {
        MessageSender.builder(this)
                .prefix("<#009dff>[<#ff7300>SchemSan<#009dff>]")
                .messageColor(TextColor.color(0, 238, 255))
                .errorColor(TextColor.color(255, 56, 89))
                .register();

        registerCommand(new SchematicClean(this, configuration));
    }
}
