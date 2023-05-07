package de.eldoria.schematicsanitizer.sanitizer.filter;

import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.world.block.BlockType;
import org.bukkit.Material;

import java.util.Set;

public record BlockFilter(Set<Material> materialBlacklist) {
    public boolean isBlacklisted(BlockType blockType) {
        return materialBlacklist.contains(BukkitAdapter.adapt(blockType));
    }
}
