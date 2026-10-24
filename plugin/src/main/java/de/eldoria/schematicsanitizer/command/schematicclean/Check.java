/*
 *     SPDX-License-Identifier: AGPL-3.0-only
 *
 *     Copyright (C) EldoriaRPG Team and Contributor
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

public class Check extends SanitizeBase {
    public Check(Plugin plugin, Report report, Configuration configuration) {
        super(
                plugin,
                CommandMeta.builder("check")
                        .addUnlocalizedArgument("schematic", true)
                        .withPermission(Permissions.Check.USE)
                        .build(),
                report,
                configuration);
    }

    @Override
    protected SanitizerReport report(Sanitizer sanitizer, Arguments args) throws IOException {
        return sanitizer.check();
    }
}
