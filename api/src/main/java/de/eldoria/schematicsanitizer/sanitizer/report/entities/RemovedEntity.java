/*
 *     SPDX-License-Identifier: LGPL-3.0-or-later
 *
 *     Copyright (C) RainbowDashLabs and Contributor
 */

package de.eldoria.schematicsanitizer.sanitizer.report.entities;

import com.sk89q.worldedit.entity.BaseEntity;
import com.sk89q.worldedit.util.Location;
import de.eldoria.schematicsanitizer.sanitizer.report.cause.EntityRemovalCause;

/**
 * Represents a removed entity
 * @param location location
 * @param type type
 * @param removalCause why it was removed
 */
public record RemovedEntity(Location location,
                            BaseEntity type,
                            EntityRemovalCause removalCause) {
}
