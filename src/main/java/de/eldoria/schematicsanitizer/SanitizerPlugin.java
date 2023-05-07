package de.eldoria.schematicsanitizer;

import com.sk89q.worldedit.WorldEdit;
import com.sk89q.worldedit.extent.clipboard.Clipboard;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardFormat;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardFormats;
import com.sk89q.worldedit.math.BlockVector3;
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
            Files.copy(getResource("example.schem"), data.resolve("example.schem"), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            getLogger().log(Level.SEVERE, "Nope", e);
        }

        getCommand("sanpaste").setExecutor(this);
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        return true;
    }

    public Clipboard loadSchematic() throws IOException {
        File cube = getDataFolder().toPath().resolve("example.schem").toFile();
        ClipboardFormat format = ClipboardFormats.findByFile(cube);
        try (var in = new FileInputStream(cube); var reader = format.getReader(in)) {
            Clipboard clipboard = reader.read();
            var dimensions = clipboard.getDimensions();
            var centerZ = clipboard.getMinimumPoint().getBlockZ() + dimensions.getBlockZ() / 2;
            var centerX = clipboard.getMinimumPoint().getBlockX() + dimensions.getBlockX() / 2;
            var centerY = clipboard.getMinimumPoint().getBlockY();
            clipboard.setOrigin(BlockVector3.at(centerX, centerY, centerZ));
            return clipboard;
        }
    }
}
