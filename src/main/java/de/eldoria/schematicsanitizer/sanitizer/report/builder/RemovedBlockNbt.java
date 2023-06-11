package de.eldoria.schematicsanitizer.sanitizer.report.builder;

import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldedit.world.block.BlockType;
import org.jetbrains.annotations.Nullable;

public record RemovedBlockNbt(BlockVector3 location, BlockType type, NbtRemovalCause removalCause, String tag, @Nullable String value) {
}
