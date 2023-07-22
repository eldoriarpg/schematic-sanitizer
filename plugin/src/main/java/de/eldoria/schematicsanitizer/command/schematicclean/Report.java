/*
 *     SPDX-License-Identifier: LGPL-3.0-or-later
 *
 *     Copyright (C) RainbowDashLabs and Contributor
 */

package de.eldoria.schematicsanitizer.command.schematicclean;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.sk89q.worldedit.WorldEdit;
import de.eldoria.eldoutilities.commands.command.AdvancedCommand;
import de.eldoria.eldoutilities.commands.command.CommandMeta;
import de.eldoria.eldoutilities.commands.command.util.CommandAssertions;
import de.eldoria.schematicsanitizer.command.schematicclean.report.Load;
import de.eldoria.schematicsanitizer.command.schematicclean.report.Page;
import de.eldoria.schematicsanitizer.command.schematicclean.report.Show;
import de.eldoria.schematicsanitizer.configuration.Configuration;
import de.eldoria.schematicsanitizer.rendering.ReportRenderer;
import de.eldoria.schematicsanitizer.sanitizer.report.SanitizerReport;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.plugin.Plugin;

import java.nio.file.Path;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class Report extends AdvancedCommand {
    private final Cache<String, ReportRenderer> fileReports = CacheBuilder.newBuilder().expireAfterAccess(30, TimeUnit.MINUTES).build();
    private final Map<UUID, ReportRenderer> reports = new HashMap<>();
    private final UUID console = new UUID(0L, 0L);
    private final Configuration configuration;
    private WorldEdit worldEdit;

    public Report(Plugin plugin, Configuration configuration) {
        super(plugin);
        this.configuration = configuration;
        Show show = new Show(plugin, this);
        meta(CommandMeta.builder("report")
                .withDefaultCommand(show)
                .withSubCommand(show)
                .withSubCommand(new Page(plugin, this))
                .withSubCommand(new Load(plugin, this))
                .build());
    }

    public void register(CommandSender sender, SanitizerReport report) {
        register(sender, ReportRenderer.create(report, configuration.settings()));
    }

    public void register(CommandSender sender, ReportRenderer renderer) {
        reports.put(getSenderUUID(sender), renderer);
        fileReports.put(buildCacheString(renderer.report().path()), renderer);
        messageSender().sendMessage(sender, renderer.component());
    }

    private String buildCacheString(Path path) {
        return path.toAbsolutePath().toString().replace(worldEdit().getSchematicsFolderPath().toAbsolutePath().toString(), "");
    }

    public boolean load(CommandSender sender, String path) {
        ReportRenderer renderer = fileReports.getIfPresent(path);
        if (renderer != null) register(sender, renderer);
        return renderer != null;
    }

    public ReportRenderer get(CommandSender sender) {
        ReportRenderer report = reports.get(getSenderUUID(sender));
        CommandAssertions.isTrue(report != null, "No report registered.");
        return report;
    }

    private UUID getSenderUUID(CommandSender sender) {
        return (sender instanceof Entity e) ? e.getUniqueId() : console;
    }

    public WorldEdit worldEdit() {
        if (worldEdit == null) worldEdit = WorldEdit.getInstance();
        return worldEdit;
    }

    public Collection<String> fileReports() {
        return fileReports.asMap().keySet();
    }

    public String addFileReport(SanitizerReport report) {
        String path = buildCacheString(report.path());
        fileReports.put(path, ReportRenderer.create(report, configuration.settings()));
        return path;
    }

    public ReportRenderer fileReport(String regPath) {
        return fileReports.getIfPresent(regPath);
    }
}
