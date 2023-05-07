package de.eldoria.schematicsanitizer.sanitizer;

import com.sk89q.worldedit.extent.clipboard.Clipboard;
import com.sk89q.worldedit.function.operation.ForwardExtentCopy;
import com.sk89q.worldedit.function.operation.Operations;
import de.eldoria.schematicsanitizer.WorldEditUtil;
import de.eldoria.schematicsanitizer.sanitizer.report.Report;
import de.eldoria.schematicsanitizer.sanitizer.settings.Settings;

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
        return sanitize(false);
    }

    public Report fix() throws IOException {
        return sanitize(true);
    }

    private Report sanitize(boolean fix) throws IOException {
        try (Clipboard clipboard = WorldEditUtil.loadSchematic(path)) {
            SanitizerExtent sanitizerExtent = new SanitizerExtent(clipboard, settings);
            ForwardExtentCopy copy = new ForwardExtentCopy(clipboard, clipboard.getRegion(), sanitizerExtent, clipboard.getMinimumPoint());
            Operations.completeBlindly(copy);
            return sanitizerExtent.report();
        }
    }
}
