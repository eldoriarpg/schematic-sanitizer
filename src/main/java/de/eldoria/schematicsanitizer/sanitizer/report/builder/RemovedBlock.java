package de.eldoria.schematicsanitizer.sanitizer.report.builder;

import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldedit.world.block.BlockType;

public record RemovedBlock(BlockVector3 location, BlockType type, BlockRemovalCause removalCause) {
}
