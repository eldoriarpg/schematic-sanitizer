package de.eldoria.schematicsanitizer.command.schematicclean.report;

import de.eldoria.eldoutilities.commands.command.AdvancedCommand;
import de.eldoria.eldoutilities.commands.command.CommandMeta;
import de.eldoria.schematicsanitizer.command.schematicclean.Report;
import de.eldoria.schematicsanitizer.sanitizer.report.SanitizerReport;
import org.bukkit.plugin.Plugin;

public class Page extends AdvancedCommand {
    public Page(Plugin plugin, Report report) {
        super(plugin, CommandMeta.builder("page")
                .withSubCommand(new ShowPage(plugin, "entities", report, SanitizerReport::entities))
                .withSubCommand(new ShowPage(plugin, "entities_nbt", report, SanitizerReport::entitiesNbt))
                .withSubCommand(new ShowPage(plugin, "blocks", report, SanitizerReport::blocks))
                .withSubCommand(new ShowPage(plugin, "blocks_nbt", report, SanitizerReport::blocksNbt))
                .build());
    }
}
