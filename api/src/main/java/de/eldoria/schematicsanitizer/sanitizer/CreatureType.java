/*
 *     SPDX-License-Identifier: LGPL-3.0-or-later
 *
 *     Copyright (C) RainbowDashLabs and Contributor
 */

package de.eldoria.schematicsanitizer.sanitizer;

import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.entity.BaseEntity;
import com.sk89q.worldedit.world.entity.EntityType;
import org.bukkit.entity.Creature;

/**
 * Type of entity
 */
public enum CreatureType {
    CREATURE, NON_CREATURE, UNKNOWN;

    public static CreatureType getType(BaseEntity base) {
        return getType(base.getType());
    }

    public static CreatureType getType(EntityType type) {
        var entityClass = BukkitAdapter.adapt(type).getEntityClass();

        if (entityClass == null) return UNKNOWN;

        return Creature.class.isAssignableFrom(entityClass) ? CREATURE : NON_CREATURE;
    }
}
