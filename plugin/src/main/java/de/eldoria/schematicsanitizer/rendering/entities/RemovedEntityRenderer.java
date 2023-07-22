/*
 *     SPDX-License-Identifier: LGPL-3.0-or-later
 *
 *     Copyright (C) RainbowDashLabs and Contributor
 */

package de.eldoria.schematicsanitizer.rendering.entities;

import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.entity.BaseEntity;
import com.sk89q.worldedit.util.Location;
import de.eldoria.schematicsanitizer.sanitizer.report.cause.EntityRemovalCause;
import de.eldoria.schematicsanitizer.sanitizer.report.entities.RemovedEntity;

public record RemovedEntityRenderer(RemovedEntity entity) implements ComponentEntityRenderer {
    @Override
    public String component() {
        return "%s %s %s".formatted(coords(location()), name(BukkitAdapter.adapt(type().getType()), type().getType()), cause(removalCause()));
    }

    public Location location() {
        return entity.location();
    }

    public BaseEntity type() {
        return entity.type();
    }

    public EntityRemovalCause removalCause() {
        return entity.removalCause();
    }
}
