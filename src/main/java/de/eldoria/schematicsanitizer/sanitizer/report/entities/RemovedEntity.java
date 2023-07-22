/*
 *     SPDX-License-Identifier: LGPL-3.0-or-later
 *
 *     Copyright (C) RainbowDashLabs and Contributor
 */

package de.eldoria.schematicsanitizer.sanitizer.report.entities;

import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.entity.BaseEntity;
import com.sk89q.worldedit.util.Location;
import de.eldoria.schematicsanitizer.sanitizer.report.cause.EntityRemovalCause;

public record RemovedEntity(Location location, BaseEntity type,
                            EntityRemovalCause removalCause) implements ComponentEntity {
    @Override
    public String component() {
        return "%s %s %s".formatted(coords(location), name(BukkitAdapter.adapt(type.getType()), type.getType()), cause(removalCause));
    }

}
