package de.eldoria.schematicsanitizer.sanitizer.filter;

import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.world.block.BlockType;
import org.bukkit.Material;

import java.util.Set;

/**
 * A class used for filtering blocks based on a blacklist of materials.
 */

public record BlockFilter(Set<Material> materialBlacklist) {
    /**
     * Checks if a given block type is blacklisted.
     *
     * @param blockType The type of block to check.
     * @return {@code true} if the block type is blacklisted, otherwise {@code false}.
     */
    public boolean isBlacklisted(BlockType blockType) {
        return materialBlacklist.contains(BukkitAdapter.adapt(blockType));
    }
}
