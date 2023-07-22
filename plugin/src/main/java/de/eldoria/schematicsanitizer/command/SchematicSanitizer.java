/*
 *     SPDX-License-Identifier: LGPL-3.0-or-later
 *
 *     Copyright (C) RainbowDashLabs and Contributor
 */

package de.eldoria.schematicsanitizer.command;

import de.eldoria.eldoutilities.commands.command.AdvancedCommand;
import de.eldoria.eldoutilities.commands.command.CommandMeta;
import de.eldoria.schematicsanitizer.command.schematicclean.Check;
import de.eldoria.schematicsanitizer.command.schematicclean.Fix;
import de.eldoria.schematicsanitizer.command.schematicclean.FixBatch;
import de.eldoria.schematicsanitizer.command.schematicclean.Report;
import de.eldoria.schematicsanitizer.configuration.Configuration;
import org.bukkit.plugin.Plugin;

public class SchematicSanitizer extends AdvancedCommand {
    public SchematicSanitizer(Plugin plugin, Configuration configuration) {
        super(plugin);
        Report report = new Report(plugin, configuration);
        meta(CommandMeta.builder("schematicsanitizer")
                .withSubCommand(new Check(plugin, report, configuration))
                .withSubCommand(new Fix(plugin, report, configuration))
                .withSubCommand(new FixBatch(plugin, configuration, report))
                .withSubCommand(report)
                .build());
    }
}
