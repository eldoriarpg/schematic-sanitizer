/*
 *     SPDX-License-Identifier: LGPL-3.0-or-later
 *
 *     Copyright (C) RainbowDashLabs and Contributor
 */

package de.eldoria.schematicsanitizer.rendering.entities;

import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.entity.BaseEntity;
import com.sk89q.worldedit.util.Location;
import de.eldoria.schematicsanitizer.sanitizer.report.cause.NbtRemovalCause;
import de.eldoria.schematicsanitizer.sanitizer.report.entities.RemovedEntityNbt;
import org.jetbrains.annotations.Nullable;

public record RemovedEntityNbtRenderer(RemovedEntityNbt entity) implements ComponentEntityRenderer {
    @Override
    public String component() {
        if (value() == null) {
            return "%s %s %s: %s".formatted(coords(location()), name(BukkitAdapter.adapt(type().getType()), type().getType()), cause(removalCause()), meta(tag()));
        }
        return "%s %s %s: %s".formatted(coords(location()), name(BukkitAdapter.adapt(type().getType()), type().getType()), cause(removalCause()), meta("{%s:%s}".formatted(tag(), value())));
    }

    public Location location() {
        return entity.location();
    }

    public BaseEntity type() {
        return entity.type();
    }

    public NbtRemovalCause removalCause() {
        return entity.removalCause();
    }

    public String tag() {
        return entity.tag();
    }

    @Nullable
    public String value() {
        return entity.value();
    }
}
