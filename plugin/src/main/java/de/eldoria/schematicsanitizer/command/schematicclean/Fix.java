/*
 *     SPDX-License-Identifier: LGPL-3.0-or-later
 *
 *     Copyright (C) RainbowDashLabs and Contributor
 */

package de.eldoria.schematicsanitizer.command.schematicclean;

import de.eldoria.eldoutilities.commands.command.CommandMeta;
import de.eldoria.eldoutilities.commands.command.util.Arguments;
import de.eldoria.schematicsanitizer.configuration.Configuration;
import de.eldoria.schematicsanitizer.sanitizer.Sanitizer;
import de.eldoria.schematicsanitizer.sanitizer.report.SanitizerReport;
import de.eldoria.schematicsanitizer.util.Permissions;
import org.bukkit.plugin.Plugin;

import java.io.IOException;

public class Fix extends SanitizeBase {

    public Fix(Plugin plugin, Report report, Configuration configuration) {
        super(plugin, CommandMeta.builder("fix")
                .addUnlocalizedArgument("schematic", true)
                .addUnlocalizedArgument("new_name", false)
                .withPermission(Permissions.Fix.USE)
                .build(), report, configuration);
    }

    @Override
    protected SanitizerReport report(Sanitizer sanitizer, Arguments args) throws IOException {
        if (args.flags().has("o")) {
            return sanitizer.fix();
        }
        return sanitizer.fix(sanitizer.name() + "_new");
    }
}
