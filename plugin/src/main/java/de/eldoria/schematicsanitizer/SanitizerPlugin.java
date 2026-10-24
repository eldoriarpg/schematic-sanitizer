/*
 *     SPDX-License-Identifier: AGPL-3.0-only
 *
 *     Copyright (C) EldoriaRPG Team and Contributor
 */
package de.eldoria.schematicsanitizer;

import com.sk89q.worldedit.WorldEdit;
import com.sk89q.worldedit.world.entity.EntityType;
import de.eldoria.eldoutilities.config.template.PluginBaseConfiguration;
import de.eldoria.eldoutilities.debug.HealthCheck;
import de.eldoria.eldoutilities.debug.data.EntryData;
import de.eldoria.eldoutilities.localization.ILocalizer;
import de.eldoria.eldoutilities.localization.Localizer;
import de.eldoria.eldoutilities.messages.MessageSender;
import de.eldoria.eldoutilities.plugin.EldoPlugin;
import de.eldoria.schematicsanitizer.command.SchematicSanitizer;
import de.eldoria.schematicsanitizer.configuration.Configuration;
import de.eldoria.schematicsanitizer.updates.Updates;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.text.minimessage.tag.Tag;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.stream.Collectors;

import static de.eldoria.schematicsanitizer.util.Colors.BAD;
import static de.eldoria.schematicsanitizer.util.Colors.GOOD;
import static de.eldoria.schematicsanitizer.util.Colors.HEADER;
import static de.eldoria.schematicsanitizer.util.Colors.INACTIVE;
import static de.eldoria.schematicsanitizer.util.Colors.INTERACT;
import static de.eldoria.schematicsanitizer.util.Colors.NAME;
import static de.eldoria.schematicsanitizer.util.Colors.SECTION;
import static de.eldoria.schematicsanitizer.util.Colors.VALUE;

@SuppressWarnings("unused")
public class SanitizerPlugin extends EldoPlugin {
    private final Configuration configuration;
    private Updates updates;

    public SanitizerPlugin() {
        configuration = new Configuration(this);
    }

    @Override
    public Level getLogLevel() {
        return configuration.secondary(PluginBaseConfiguration.KEY).logLevel();
    }

    @Override
    public void onPluginEnable() {
        ILocalizer localizer =
                Localizer.builder(this, "en_US").setIncludedLocales("en_US").build();
        MessageSender.builder(this)
                .localizer(localizer)
                .prefix("<#009dff>[<#ff7300>SC<#009dff>]")
                .addTag(tags -> tags.tag("header", Tag.styling(TextColor.color(HEADER.getRGB()), TextDecoration.BOLD))
                        .tag("name", Tag.styling(TextColor.color(NAME.getRGB())))
                        .tag("value", Tag.styling(TextColor.color(VALUE.getRGB())))
                        .tag("bad", Tag.styling(TextColor.color(BAD.getRGB())))
                        .tag("good", Tag.styling(TextColor.color(GOOD.getRGB())))
                        .tag("interact", Tag.styling(TextColor.color(INTERACT.getRGB())))
                        .tag("section", Tag.styling(TextColor.color(SECTION.getRGB())))
                        .tag("inactive", Tag.styling(TextColor.color(INACTIVE.getRGB()))))
                .messageColor(TextColor.color(0, 238, 255))
                .errorColor(TextColor.color(255, 56, 89))
                .register();

        registerCommand(new SchematicSanitizer(this, configuration));

        configuration.main();
        configuration.save();
        updates = new Updates(this, configuration);
        updates.apply();
    }

    @Override
    public void onPluginDisable() {
        if (updates != null) updates.stop();
    }

    @Override
    public void onPostStart() throws Throwable {
        configuration.settings();
        configuration.saveSettings();

        String collect = EntityType.REGISTRY.getMap().values().stream()
                .map(EntityType::getId)
                .collect(Collectors.joining("\n"));
        try {
            Files.writeString(getDataFolder().toPath().resolve("entity_types.txt"), collect);
        } catch (IOException e) {
            logger().log(Level.WARNING, "Could not dump entities");
        }
    }

    @Override
    public @NotNull EntryData[] getDebugInformations() {
        return new EntryData[] {
            new EntryData(
                    "Environment",
                    Map.of(
                            "FastAsyncWorldEdit", faweVersion(),
                            "Schematic folder", schematicFolder().toString()))
        };
    }

    @Override
    public @NotNull List<HealthCheck> getHealthChecks() {
        return List.of(faweHealth(), schematicFolderHealth());
    }

    private HealthCheck faweHealth() {
        Plugin fawe = getServer().getPluginManager().getPlugin("FastAsyncWorldEdit");
        if (fawe == null || !fawe.isEnabled()) {
            return HealthCheck.error("FastAsyncWorldEdit", "Not installed or not enabled");
        }
        return HealthCheck.ok(
                "FastAsyncWorldEdit", "Version " + fawe.getPluginMeta().getVersion());
    }

    private HealthCheck schematicFolderHealth() {
        Path folder = schematicFolder();
        if (!Files.isDirectory(folder)) return HealthCheck.warn("Schematic folder", "Does not exist: " + folder);
        if (!Files.isReadable(folder)) return HealthCheck.error("Schematic folder", "Not readable: " + folder);
        return HealthCheck.ok("Schematic folder", folder.toString());
    }

    private String faweVersion() {
        Plugin fawe = getServer().getPluginManager().getPlugin("FastAsyncWorldEdit");
        return fawe == null ? "not installed" : fawe.getPluginMeta().getVersion();
    }

    private Path schematicFolder() {
        return WorldEdit.getInstance().getSchematicsFolderPath();
    }
}
