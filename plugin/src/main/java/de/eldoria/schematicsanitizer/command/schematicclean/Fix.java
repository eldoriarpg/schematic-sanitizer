/*
 *     SPDX-License-Identifier: AGPL-3.0-only
 *
 *     Copyright (C) EldoriaRPG Team and Contributor
 */
package de.eldoria.schematicsanitizer.command.schematicclean;

import de.eldoria.eldoutilities.commands.command.CommandMeta;
import de.eldoria.eldoutilities.commands.command.util.Arguments;
import de.eldoria.eldoutilities.commands.exceptions.CommandException;
import de.eldoria.schematicsanitizer.configuration.Configuration;
import de.eldoria.schematicsanitizer.sanitizer.Sanitizer;
import de.eldoria.schematicsanitizer.sanitizer.report.SanitizerReport;
import de.eldoria.schematicsanitizer.util.Permissions;
import de.eldoria.schematicsanitizer.util.SchematicPaths;
import org.bukkit.plugin.Plugin;

import java.io.IOException;
import java.nio.file.Path;

public class Fix extends SanitizeBase {

    public Fix(Plugin plugin, Report report, Configuration configuration) {
        super(
                plugin,
                CommandMeta.builder("fix")
                        .addUnlocalizedArgument("schematic", true)
                        .addUnlocalizedArgument("new_name", false)
                        .withPermission(Permissions.Fix.USE)
                        .build(),
                report,
                configuration);
    }

    @Override
    protected void checkArguments(Path path, Arguments args) throws CommandException {
        if (args.flags().has("o") || !args.sizeIs(2)) return;
        SchematicPaths.sibling(worldEdit().getSchematicsFolderPath(), path, Sanitizer.fileName(args.asString(1)))
                .orElseThrow(() -> CommandException.message("The new name must be a file name in the same folder"));
    }

    @Override
    protected SanitizerReport report(Sanitizer sanitizer, Arguments args) throws IOException {
        if (args.flags().has("o")) {
            return sanitizer.fix();
        }
        return sanitizer.fix(args.asString(1, sanitizer.name() + "_new"));
    }
}
