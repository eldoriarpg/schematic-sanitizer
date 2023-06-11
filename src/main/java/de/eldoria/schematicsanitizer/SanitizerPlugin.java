package de.eldoria.schematicsanitizer;

import com.sk89q.worldedit.WorldEdit;
import com.sk89q.worldedit.extent.clipboard.Clipboard;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardFormat;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardFormats;
import com.sk89q.worldedit.math.BlockVector3;
import de.eldoria.schematicsanitizer.sanitizer.Sanitizer;
import de.eldoria.schematicsanitizer.sanitizer.settings.Settings;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
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
            sanitizer = Sanitizer.create(worldEdit.getSchematicsFolderPath().resolve(args[0]), Settings.DEFAULT);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            sanitizer.check();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return true;
    }
}
