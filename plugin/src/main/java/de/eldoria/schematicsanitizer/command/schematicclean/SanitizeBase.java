/*
 *     SPDX-License-Identifier: LGPL-3.0-or-later
 *
 *     Copyright (C) RainbowDashLabs and Contributor
 */

package de.eldoria.schematicsanitizer.command.schematicclean;

import com.sk89q.worldedit.WorldEdit;
import de.eldoria.eldoutilities.commands.command.AdvancedCommand;
import de.eldoria.eldoutilities.commands.command.CommandMeta;
import de.eldoria.eldoutilities.commands.command.util.Arguments;
import de.eldoria.eldoutilities.commands.exceptions.CommandException;
import de.eldoria.eldoutilities.commands.executor.ITabExecutor;
import de.eldoria.schematicsanitizer.configuration.Configuration;
import de.eldoria.schematicsanitizer.sanitizer.Sanitizer;
import de.eldoria.schematicsanitizer.sanitizer.report.SanitizerReport;
import de.eldoria.schematicsanitizer.util.Completion;
import de.eldoria.schematicsanitizer.util.SchematicPaths;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;

public abstract class SanitizeBase extends AdvancedCommand implements ITabExecutor {
    private final Report report;
    private final Configuration configuration;
    private WorldEdit worldEdit;

    public SanitizeBase(Plugin plugin, CommandMeta commandMeta, Report report, Configuration configuration) {
        super(plugin, commandMeta);
        this.report = report;
        this.configuration = configuration;
    }

    @Override
    public void onCommand(@NotNull CommandSender sender, @NotNull String alias, @NotNull Arguments args) throws CommandException {
        Path path = SchematicPaths.resolve(worldEdit().getSchematicsFolderPath(), args.asString(0))
                .orElseThrow(() -> CommandException.message("Invalid path"));
        if (!path.toFile().exists()) throw CommandException.message("Unknown file");
        if (path.toFile().isDirectory()) throw CommandException.message("Only a single file can be processed.");
        checkArguments(path, args);

        Sanitizer sanitizer;
        try {
            sanitizer = Sanitizer.create(path, configuration.settings());
        } catch (IOException e) {
            plugin().getLogger().log(Level.SEVERE, "Could not load schematic.", e);
            throw CommandException.message("Could not load schematic.");
        }
        plugin().getServer().getScheduler().runTaskAsynchronously(plugin(), () -> {
            try {
                SanitizerReport report = report(sanitizer, args);
                this.report.register(sender, report);
            } catch (IOException | RuntimeException e) {
                plugin().getLogger().log(Level.SEVERE, "Could not process schematic", e);
                handleCommandError(sender, CommandException.message("Something went wrong. Please check the console"));
            }
        });
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull String alias, @NotNull Arguments args) throws CommandException {
        if (args.sizeIs(1)) {
            return Completion.completeAll(worldEdit().getSchematicsFolderPath(), args.asString(0));
        }
        return Collections.emptyList();
    }

    /**
     * Checks the arguments after the schematic before any work starts.
     *
     * @param path the schematic, already checked to be inside the schematic folder
     * @param args the command arguments
     * @throws CommandException when an argument is not acceptable
     */
    protected void checkArguments(Path path, Arguments args) throws CommandException {}

    protected abstract SanitizerReport report(Sanitizer sanitizer, Arguments args) throws IOException;

    public WorldEdit worldEdit() {
        if (worldEdit == null) worldEdit = WorldEdit.getInstance();
        return worldEdit;
    }
}
