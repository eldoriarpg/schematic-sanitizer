package de.eldoria.schematicsanitizer.command.schematicclean;

import de.eldoria.eldoutilities.commands.command.CommandMeta;
import de.eldoria.eldoutilities.commands.command.util.Arguments;
import de.eldoria.schematicsanitizer.configuration.Configuration;
import de.eldoria.schematicsanitizer.sanitizer.Sanitizer;
import de.eldoria.schematicsanitizer.sanitizer.report.SanitizerReport;
import org.bukkit.plugin.Plugin;

import java.io.IOException;

public class Fix extends SanitizeBase {

    public Fix(Plugin plugin, Report report, Configuration configuration) {
        super(plugin, CommandMeta.builder("fix")
                .addUnlocalizedArgument("schematic", true)
                .addUnlocalizedArgument("new_name", false)
                .build(), report, configuration);
    }

    @Override
    protected SanitizerReport report(Sanitizer sanitizer, Arguments args) throws IOException {
        return sanitizer.fix(args.asString(1, args.asString(0, args.flags().has("o") ? sanitizer.name() : sanitizer.name() + "_clean")));
    }
}
