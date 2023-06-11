package de.eldoria.schematicsanitizer;

import com.sk89q.worldedit.WorldEdit;
import de.eldoria.schematicsanitizer.sanitizer.Sanitizer;
import de.eldoria.schematicsanitizer.sanitizer.report.Report;
import de.eldoria.schematicsanitizer.sanitizer.settings.Settings;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;

public class SanitizerPlugin extends JavaPlugin {
    private WorldEdit worldEdit;

    @Override
    public void onEnable() {
        worldEdit = WorldEdit.getInstance();

        try {
            Path data = getDataFolder().toPath();
            Files.createDirectories(data);
            Files.copy(getResource("terrain.schem"), data.resolve("terrain.schem"), StandardCopyOption.REPLACE_EXISTING);
            Files.copy(getResource("bowlinglane.schem"), data.resolve("bowlinglane.schem"), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            getLogger().log(Level.SEVERE, "Nope", e);
        }

        getCommand("sanpaste").setExecutor(this);
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        Sanitizer sanitizer = null;
        try {
            sanitizer = Sanitizer.create(worldEdit.getSchematicsFolderPath().resolve(args[1]), Settings.DEFAULT);
        } catch (IOException e) {
            return false;
        }
        try {
            Report report;
            if (args[0].equals("check")) {
                report = sanitizer.check();
            } else if (args[0].equals("fix")) {
                report = args.length > 2 ? sanitizer.fix(args[2]) : sanitizer.fix();
            }else {
                return false;
            }
            sender.sendMessage(report.toString());
        } catch (IOException e) {
            getLogger().log(Level.SEVERE, "ups", e);
            return false;
        }
        return true;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {
        if (args.length == 1) {
            return List.of("fix", "check");
        }

        if (args.length == 2) {
            return Arrays.stream(worldEdit.getSchematicsFolderPath().toFile().listFiles(File::isFile)).map(File::getName).filter(name -> name.startsWith(args[1])).toList();
        }
        return Collections.emptyList();
    }
}
