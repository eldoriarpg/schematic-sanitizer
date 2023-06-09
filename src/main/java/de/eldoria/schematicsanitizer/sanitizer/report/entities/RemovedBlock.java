package de.eldoria.schematicsanitizer.sanitizer.report.entities;

import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldedit.world.block.BlockType;
import de.eldoria.schematicsanitizer.sanitizer.report.cause.BlockRemovalCause;

public record RemovedBlock(BlockVector3 location, BlockType type,
                           BlockRemovalCause removalCause) implements ComponentEntity {
    @Override
    public String component() {
        return "%s %s %s".formatted(coords(location), name(BukkitAdapter.adapt(type)), cause(removalCause));
    }
}
