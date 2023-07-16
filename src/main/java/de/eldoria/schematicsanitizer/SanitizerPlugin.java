package de.eldoria.schematicsanitizer;

import de.eldoria.eldoutilities.config.template.PluginBaseConfiguration;
import de.eldoria.eldoutilities.localization.ILocalizer;
import de.eldoria.eldoutilities.localization.Localizer;
import de.eldoria.eldoutilities.messages.MessageSender;
import de.eldoria.eldoutilities.plugin.EldoPlugin;
import de.eldoria.schematicsanitizer.command.SchematicSaniziter;
import de.eldoria.schematicsanitizer.configuration.Configuration;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.text.minimessage.tag.Tag;

import java.util.logging.Level;

import static de.eldoria.schematicsanitizer.util.Colors.*;

@SuppressWarnings("unused")
public class SanitizerPlugin extends EldoPlugin {
    private final Configuration configuration;

    public SanitizerPlugin() {
        // Create base configuration
        configuration = new Configuration(this);
        configuration.settings();
        configuration.save(Configuration.SETTINGS);
    }

    @Override
    public Level getLogLevel() {
        return configuration.secondary(PluginBaseConfiguration.KEY).logLevel();
    }

    @Override
    public void onPluginEnable() {
        ILocalizer localizer = Localizer.create(this, "en_US");
        MessageSender.builder(this)
                .localizer(localizer)
                .prefix("<#009dff>[<#ff7300>SC<#009dff>]")
                .addTag(tags -> tags
                        .tag("header", Tag.styling(TextColor.color(HEADER.getRGB()), TextDecoration.BOLD))
                        .tag("name", Tag.styling(TextColor.color(NAME.getRGB())))
                        .tag("value", Tag.styling(TextColor.color(VALUE.getRGB())))
                        .tag("bad", Tag.styling(TextColor.color(BAD.getRGB())))
                        .tag("good", Tag.styling(TextColor.color(GOOD.getRGB())))
                        .tag("interact", Tag.styling(TextColor.color(INTERACT.getRGB())))
                        .tag("section", Tag.styling(TextColor.color(SECTION.getRGB())))
                        .tag("inactive", Tag.styling(TextColor.color(INACTIVE.getRGB())))
                )
                .messageColor(TextColor.color(0, 238, 255))
                .errorColor(TextColor.color(255, 56, 89))
                .register();

        registerCommand(new SchematicSaniziter(this, configuration));
    }
}
