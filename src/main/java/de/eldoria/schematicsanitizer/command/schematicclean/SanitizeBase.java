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
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;

public abstract class SanitizeBase extends AdvancedCommand implements ITabExecutor {
    private final WorldEdit worldEdit;
    private final Report report;
    private final Configuration configuration;

    public SanitizeBase(Plugin plugin, CommandMeta commandMeta, Report report, Configuration configuration) {
        super(plugin, commandMeta);
        this.report = report;
        this.configuration = configuration;
        worldEdit = WorldEdit.getInstance();
    }

    @Override
    public void onCommand(@NotNull CommandSender sender, @NotNull String alias, @NotNull Arguments args) throws CommandException {
        Sanitizer sanitizer;
        try {
            sanitizer = Sanitizer.create(worldEdit.getSchematicsFolderPath().resolve(args.asString(0)), configuration.settings());
        } catch (IOException e) {
            plugin().getLogger().log(Level.SEVERE, "Could not load schematic.", e);
            throw CommandException.message("Could not load schematic.");
        }
        try {
            SanitizerReport report = report(sanitizer, args);
            this.report.register(sender, report);
        } catch (IOException e) {
            plugin().getLogger().log(Level.SEVERE, "Could not process schematic", e);
            throw CommandException.message("Something went wrong. Please check the console");
        }
    }

    protected abstract SanitizerReport report(Sanitizer sanitizer, Arguments args) throws IOException;

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull String alias, @NotNull Arguments args) throws CommandException {
        if (args.sizeIs(1)) {
            return Arrays.stream(worldEdit.getSchematicsFolderPath().toFile().listFiles(File::isFile))
                    .map(File::getName)
                    .filter(name -> name.startsWith(args.asString(0)))
                    .toList();
        }
        return Collections.emptyList();
    }
}
