/*
 *     SPDX-License-Identifier: LGPL-3.0-or-later
 *
 *     Copyright (C) RainbowDashLabs and Contributor
 */

package de.eldoria.schematicsanitizer.command.schematicclean.report;

import de.eldoria.eldoutilities.commands.command.AdvancedCommand;
import de.eldoria.eldoutilities.commands.command.CommandMeta;
import de.eldoria.eldoutilities.commands.command.util.Arguments;
import de.eldoria.eldoutilities.commands.exceptions.CommandException;
import de.eldoria.eldoutilities.commands.executor.ITabExecutor;
import de.eldoria.schematicsanitizer.command.schematicclean.Report;
import de.eldoria.schematicsanitizer.configuration.Configuration;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

public class Show extends AdvancedCommand implements ITabExecutor {
    private final Report report;
    private final Configuration configuration;

    public Show(Plugin plugin, Report report, Configuration configuration) {
        super(plugin, CommandMeta.builder("show")
                .build());
        this.report = report;
        this.configuration = configuration;
    }

    @Override
    public void onCommand(@NotNull CommandSender sender, @NotNull String alias, @NotNull Arguments args) throws CommandException {
        messageSender().sendMessage(sender, report.get(sender).component());
    }
}
