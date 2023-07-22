/*
 *     SPDX-License-Identifier: LGPL-3.0-or-later
 *
 *     Copyright (C) RainbowDashLabs and Contributor
 */

package de.eldoria.schematicsanitizer.sanitizer.limit;

import org.bukkit.entity.Creature;

/**
 * The limit for content inside a schematic
 * @param blocks max amount of blocks
 * @param nonAirBlocks max amount of non-air blocks
 * @param creatures max amount of {@link Creature}s
 * @param nonCreatures max amount of non {@link Creature}s
 */
public record ContentLimit(int blocks, int nonAirBlocks, int creatures,
                           int nonCreatures) {
}
