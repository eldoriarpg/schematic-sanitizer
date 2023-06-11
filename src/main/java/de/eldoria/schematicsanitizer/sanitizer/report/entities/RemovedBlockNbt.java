package de.eldoria.schematicsanitizer.sanitizer.report.entities;

import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldedit.world.block.BlockType;
import de.eldoria.schematicsanitizer.sanitizer.report.cause.NbtRemovalCause;
import org.jetbrains.annotations.Nullable;

public record RemovedBlockNbt(BlockVector3 location, BlockType type, NbtRemovalCause removalCause, String tag,
                              @Nullable String value) {
}
