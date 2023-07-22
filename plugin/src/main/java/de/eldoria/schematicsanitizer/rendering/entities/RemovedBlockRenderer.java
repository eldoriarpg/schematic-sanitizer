/*
 *     SPDX-License-Identifier: LGPL-3.0-or-later
 *
 *     Copyright (C) RainbowDashLabs and Contributor
 */

package de.eldoria.schematicsanitizer.rendering.entities;

import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldedit.world.block.BlockType;
import de.eldoria.schematicsanitizer.sanitizer.report.cause.BlockRemovalCause;
import de.eldoria.schematicsanitizer.sanitizer.report.entities.RemovedBlock;

public record RemovedBlockRenderer(RemovedBlock block) implements ComponentEntityRenderer {
    @Override
    public String component() {
        return "%s %s %s".formatted(coords(location()), name(BukkitAdapter.adapt(type()), type()), cause(removalCause()));
    }

    public BlockVector3 location() {
        return block.location();
    }

    public BlockType type() {
        return block.type();
    }

    public BlockRemovalCause removalCause() {
        return block.removalCause();
    }
}
