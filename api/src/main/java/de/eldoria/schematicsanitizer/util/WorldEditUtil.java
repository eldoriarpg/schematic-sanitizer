/*
 *     SPDX-License-Identifier: LGPL-3.0-or-later
 *
 *     Copyright (C) RainbowDashLabs and Contributor
 */

package de.eldoria.schematicsanitizer.util;

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
        if(format == null) throw new IOException("Unknown file format");
        try (var in = new FileInputStream(file); var reader = format.getReader(in)) {
            return reader.read();
        }
    }

    public static Clipboard loadSchematic(Path file) throws IOException {
        return loadSchematic(file.toFile());
    }

}
