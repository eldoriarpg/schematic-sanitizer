package de.eldoria.schematicsanitizer.command;

import de.eldoria.eldoutilities.commands.command.AdvancedCommand;
import de.eldoria.eldoutilities.commands.command.CommandMeta;
import de.eldoria.schematicsanitizer.command.schematicclean.Check;
import de.eldoria.schematicsanitizer.command.schematicclean.Fix;
import de.eldoria.schematicsanitizer.command.schematicclean.Report;
import de.eldoria.schematicsanitizer.configuration.Configuration;
import org.bukkit.plugin.Plugin;

public class SchematicClean extends AdvancedCommand {
    public SchematicClean(Plugin plugin, Configuration configuration) {
        super(plugin);
        Report report = new Report(plugin, configuration);
        meta(CommandMeta.builder("schematiccleaner")
                .withSubCommand(new Check(plugin, report, configuration))
                .withSubCommand(new Fix(plugin, report, configuration))
                .withSubCommand(report)
                .build());
    }
}
