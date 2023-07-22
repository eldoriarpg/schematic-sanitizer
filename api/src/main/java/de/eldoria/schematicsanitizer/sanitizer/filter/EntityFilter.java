/*
 *     SPDX-License-Identifier: LGPL-3.0-or-later
 *
 *     Copyright (C) RainbowDashLabs and Contributor
 */

package de.eldoria.schematicsanitizer.sanitizer.filter;

import com.sk89q.worldedit.world.entity.EntityType;
import org.bukkit.entity.Creature;

import java.util.Set;

/**
 * A filter for entities
 * @param removeCreature remove entities which are classified as {@link Creature}
 * @param removeNonCreatures remove entities which are not classified as {@link Creature}
 * @param entityBlacklist remove entities which have the exact type
 */
public record EntityFilter(boolean removeCreature,
                           boolean removeNonCreatures,
                           Set<EntityType> entityBlacklist) {
    /**
     * Checks if the given entity type is blacklisted.
     *
     * @param entityType the entity type to be checked
     * @return true if the entity type is blacklisted, false otherwise
     */
    public boolean isBlacklisted(EntityType entityType) {
        return entityBlacklist.contains(entityType);
    }
}
