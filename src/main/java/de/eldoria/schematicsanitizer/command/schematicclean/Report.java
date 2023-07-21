/*
 *     SPDX-License-Identifier: LGPL-3.0-or-later
 *
 *     Copyright (C) RainbowDashLabs and Contributor
 */

package de.eldoria.schematicsanitizer.command.schematicclean;

import de.eldoria.eldoutilities.commands.command.AdvancedCommand;
import de.eldoria.eldoutilities.commands.command.CommandMeta;
import de.eldoria.eldoutilities.commands.command.util.CommandAssertions;
import de.eldoria.schematicsanitizer.command.schematicclean.report.Page;
import de.eldoria.schematicsanitizer.command.schematicclean.report.Show;
import de.eldoria.schematicsanitizer.configuration.Configuration;
import de.eldoria.schematicsanitizer.sanitizer.report.SanitizerReport;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.plugin.Plugin;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Report extends AdvancedCommand {
    private final Map<UUID, SanitizerReport> reports = new HashMap<>();
    private final UUID console = new UUID(0L, 0L);
    private final Configuration configuration;

    public Report(Plugin plugin, Configuration configuration) {
        super(plugin);
        this.configuration = configuration;
        Show show = new Show(plugin, this, configuration);
        meta(CommandMeta.builder("report")
                .withDefaultCommand(show)
                .withSubCommand(show)
                .withSubCommand(new Page(plugin, this))
                .build());
    }

    public void register(CommandSender sender, SanitizerReport report) {
        reports.put(getSenderUUID(sender), report);
        messageSender().sendMessage(sender, report.component(configuration.settings()));
    }

    public SanitizerReport get(CommandSender sender) {
        SanitizerReport report = reports.get(getSenderUUID(sender));
        CommandAssertions.isTrue(report != null, "No report registered.");
        return report;
    }

    private UUID getSenderUUID(CommandSender sender) {
        return (sender instanceof Entity e) ? e.getUniqueId() : console;
    }
}
