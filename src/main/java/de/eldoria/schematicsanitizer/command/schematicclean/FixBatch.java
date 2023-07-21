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
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
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
        Path path;
        try {
            path = worldEdit.getSchematicsFolderPath().resolve(args.asString(0).replace("\\_", " "));
        } catch (InvalidPathException e) {
            throw CommandException.message("Invalid path");
        }

        if (!path.toAbsolutePath().startsWith(worldEdit.getSchematicsFolderPath().toAbsolutePath())) {
            throw CommandException.message("Invalid directory");
        }
        if (!path.toFile().exists()) throw CommandException.message("Directory does not exist");
        if (!path.toFile().isDirectory()) throw CommandException.message("Not a directory");

        var newPath = path.getParent().resolve(path.getFileName() + "_new");

        try {
            Files.createDirectories(newPath);
        } catch (FileAlreadyExistsException e) {
            // ignore
        } catch (IOException e) {
            plugin().getLogger().log(Level.SEVERE, "Could not create directory", e);
            throw CommandException.message("Could not create new directory");
        }

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
                            report = sanitizer.fix(newPath.resolve(curr.getFileName()));
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
            return Completion.completeDirectories(worldEdit.getSchematicsFolderPath(), args.asString(0));
        }
        return Collections.emptyList();
    }
}
