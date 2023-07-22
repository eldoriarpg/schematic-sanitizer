/*
 *     SPDX-License-Identifier: LGPL-3.0-or-later
 *
 *     Copyright (C) RainbowDashLabs and Contributor
 */

package de.eldoria.schematicsanitizer.rendering.entities;

import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldedit.world.block.BlockType;
import de.eldoria.schematicsanitizer.sanitizer.report.cause.NbtRemovalCause;
import de.eldoria.schematicsanitizer.sanitizer.report.entities.RemovedBlockNbt;
import org.jetbrains.annotations.Nullable;

public record RemovedBlockNbtRenderer(RemovedBlockNbt block) implements ComponentEntityRenderer {
    @Override
    public String component() {
        if (value() == null) {
            return "%s %s %s: %s".formatted(coords(location()), name(BukkitAdapter.adapt(type()), type()), cause(removalCause()), meta(tag()));
        }
        return "%s %s %s: %s".formatted(coords(location()), name(BukkitAdapter.adapt(type()),type()), cause(removalCause()), meta("{%s:%s}".formatted(tag(), value())));
    }

    public BlockVector3 location() {
        return block.location();
    }

    public BlockType type() {
        return block.type();
    }

    public NbtRemovalCause removalCause() {
        return block.removalCause();
    }

    public String tag() {
        return block.tag();
    }

    @Nullable
    public String value() {
        return block.value();
    }
}
