/*
 *     SPDX-License-Identifier: LGPL-3.0-or-later
 *
 *     Copyright (C) RainbowDashLabs and Contributor
 */

package de.eldoria.schematicsanitizer.sanitizer.report.entities;

import com.sk89q.worldedit.entity.BaseEntity;
import com.sk89q.worldedit.util.Location;
import de.eldoria.schematicsanitizer.sanitizer.report.cause.NbtRemovalCause;
import org.jetbrains.annotations.Nullable;

/**
 * Represents a removed nbt entry of an entity
 * @param location location
 * @param type type
 * @param removalCause why it was removed
 * @param tag removed tag
 * @param value removed value if it contained invalid text
 */
public record RemovedEntityNbt(Location location,
                               BaseEntity type,
                               NbtRemovalCause removalCause,
                               String tag,
                               @Nullable String value) {
}
