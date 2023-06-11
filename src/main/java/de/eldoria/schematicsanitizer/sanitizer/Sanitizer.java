package de.eldoria.schematicsanitizer.sanitizer;

import com.sk89q.worldedit.extent.clipboard.BlockArrayClipboard;
import com.sk89q.worldedit.extent.clipboard.Clipboard;
import com.sk89q.worldedit.extent.clipboard.io.BuiltInClipboardFormat;
import com.sk89q.worldedit.function.operation.ForwardExtentCopy;
import com.sk89q.worldedit.function.operation.Operations;
import de.eldoria.schematicsanitizer.WorldEditUtil;
import de.eldoria.schematicsanitizer.sanitizer.report.Report;
import de.eldoria.schematicsanitizer.sanitizer.settings.Settings;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;

public class Sanitizer {
    private final Path path;
    private final Settings settings;

    private Sanitizer(Path path, Settings settings) {
        this.path = path;
        this.settings = settings;
    }

    public static Sanitizer create(Path path, Settings settings) throws IOException {
        return new Sanitizer(path, settings);
    }

    public Report check() throws IOException {
        return sanitize().report();
    }

    public Report fix() throws IOException {
        return fix(path);
    }

    public Report fix(String newName) throws IOException {
        return fix(path.getParent().resolve(newName + ".schem"));
    }
    public Report fix(Path newPath) throws IOException {
        SanitizerExtent sanitize = sanitize();
        try (var writer = BuiltInClipboardFormat.FAST.getWriter(new FileOutputStream(newPath.toFile()))) {
            writer.write(sanitize);
        }
        return sanitize.report();
    }

    private SanitizerExtent sanitize() throws IOException {
        try (Clipboard clipboard = WorldEditUtil.loadSchematic(path)) {
            SanitizerExtent sanitizerExtent = new SanitizerExtent(clipboard, settings);
            ForwardExtentCopy copy = new ForwardExtentCopy(clipboard, clipboard.getRegion(), sanitizerExtent, clipboard.getMinimumPoint());
            Operations.completeBlindly(copy);
            return sanitizerExtent;
        }
    }
}
