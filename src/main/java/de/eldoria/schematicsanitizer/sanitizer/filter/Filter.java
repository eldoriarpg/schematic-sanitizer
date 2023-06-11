package de.eldoria.schematicsanitizer.sanitizer.filter;

import com.sk89q.worldedit.util.nbt.BinaryTag;
import com.sk89q.worldedit.util.nbt.BinaryTagType;
import com.sk89q.worldedit.util.nbt.BinaryTagTypes;
import com.sk89q.worldedit.util.nbt.CompoundBinaryTag;
import de.eldoria.schematicsanitizer.sanitizer.report.builder.ReportBuilder;

import java.util.Set;

public record Filter(BlockFilter blockFilter, EntityFilter entityFilter, Set<String> textBlacklist,
                     Set<String> nbtBlacklist) {

}
