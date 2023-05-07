package de.eldoria.schematicsanitizer;

import com.sk89q.worldedit.extent.clipboard.Clipboard;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardFormat;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardFormats;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Path;

public class WorldEditUtil {
    public static Clipboard loadSchematic(File file) throws IOException {
        ClipboardFormat format = ClipboardFormats.findByFile(file);
        try (var in = new FileInputStream(file); var reader = format.getReader(in)) {
            return reader.read();
        }
    }

    public static Clipboard loadSchematic(Path file) throws IOException {
        return loadSchematic(file.toFile());
    }

}
