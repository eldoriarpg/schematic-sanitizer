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
import de.eldoria.schematicsanitizer.util.Text;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;

public class FixBatch extends AdvancedCommand implements ITabExecutor {
    private final WorldEdit worldEdit;
    private final Configuration configuration;

    public FixBatch(Plugin plugin, Configuration configuration) {
        super(plugin, CommandMeta.builder("fixbatch")
                .addArgument("directory", true)
                .build());
        this.configuration = configuration;
        worldEdit = WorldEdit.getInstance();
    }

    @Override
    public void onCommand(@NotNull CommandSender sender, @NotNull String alias, @NotNull Arguments args) throws CommandException {
        Path path = worldEdit.getSchematicsFolderPath().resolve(args.asString(0).replace("\\_", " "));

        if (!path.toAbsolutePath().startsWith(worldEdit.getSchematicsFolderPath().toAbsolutePath())) {
            throw CommandException.message("Invalid directory");
        }
        if (!path.toFile().exists()) throw CommandException.message("Directory does not exist");
        if (!path.toFile().isDirectory()) throw CommandException.message("Not a directory");

        try (var files = Files.list(path)) {
            List<Path> list = files
                    .filter(p -> p.toFile().isFile())
                    .filter(p -> {
                        String name = p.toFile().getName();
                        return name.endsWith(".schem") || name.endsWith(".schematic");
                    })
                    .toList();
            plugin().getServer().getScheduler().runTaskAsynchronously(plugin(), () -> {
                messageSender().sendMessage(sender, "Starting batch. Processing %d files".formatted(list.size()));
                for (Path curr : list) {
                    messageSender().sendMessage(sender, "Checking <value>" + curr.getFileName());
                    Sanitizer sanitizer;
                    try {
                        sanitizer = Sanitizer.create(curr, configuration.settings());
                    } catch (Throwable e) {
                        plugin().getLogger().log(Level.SEVERE, "Could not load schematic.", e);
                        handleCommandError(sender, CommandException.message("Could not load schematic " + curr));
                        continue;
                    }
                    try {
                        SanitizerReport report;
                        if (args.flags().has("o")) {
                            report = sanitizer.fix();
                        } else {
                            report = sanitizer.fix(sanitizer.name() + "_new");
                        }
                        messageSender().sendMessage(sender, report.shortComponent(configuration.settings()));
                    } catch (Throwable e) {
                        plugin().getLogger().log(Level.SEVERE, "Could not process schematic", e);
                        handleCommandError(sender, CommandException.message("Something went wrong. Please check the console"));
                    }
                }
                messageSender().sendMessage(sender, "Batch process finished.");
            });
        } catch (IOException e) {
            plugin().getLogger().log(Level.SEVERE, "Could not process schematic", e);
            handleCommandError(sender, CommandException.message("Something went wrong. Please check the console"));
        }
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull String alias, @NotNull Arguments args) throws CommandException {
        if (args.sizeIs(1)) {
            Path schem = worldEdit.getSchematicsFolderPath();
            String arg = args.asString(0);
            if (arg.isBlank()) return complete(schem, schem);
            String path = arg.replace("\\_", " ");
            if (path.contains("..")) return Collections.singletonList("No stepping out c:");
            if (path.endsWith("/")) {
                // list all files and directories in directory
                return complete(schem, schem.resolve(path));
            }
            if (path.contains("/")) {
                path = arg.replace("/.+?", "");
                return complete(schem, schem.resolve(path)).stream().filter(p -> p.startsWith(arg)).toList();
            }
            return complete(schem, schem).stream().filter(p -> p.startsWith(arg)).toList();
        }
        return Collections.emptyList();
    }

    private List<String> complete(Path base, Path path) {
        if (!path.toAbsolutePath().startsWith(base.toAbsolutePath())) return Collections.emptyList();
        try (var stream = Files.list(path)) {
            return stream
                    .filter(p -> p.toFile().isDirectory())
                    .map(p -> p + "/")
                    .map(p -> p.replace(base + "/", ""))
                    .map(Text::unifyPath)
                    .map(p -> p.replace(" ", "\\_"))
                    .filter(p -> !p.isBlank())
                    .toList();
        } catch (IOException e) {
            return Collections.emptyList();
        }
    }

}
