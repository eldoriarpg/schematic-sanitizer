/*
 *     SPDX-License-Identifier: LGPL-3.0-or-later
 *
 *     Copyright (C) RainbowDashLabs and Contributor
 */

package de.eldoria.schematicsanitizer.sanitizer.report.subreports;

import org.bukkit.entity.Creature;

/**
 * Report about the content of the schematic
 *
 * @param blocks            amount of blocks including blacklisted
 * @param nonAirBlocks      amount of non-air blocks including blacklisted
 * @param totalCreatures    amount of {@link Creature}s including blacklisted
 * @param totalNonCreatures amount of non {@link Creature}s including blacklisted
 * @param creatures         amount of {@link Creature}s
 * @param nonCreatures      amount of non {@link Creature}s
 */
public record ContentReport(int blocks,
                            int nonAirBlocks,
                            int totalCreatures,
                            int totalNonCreatures,
                            int creatures,
                            int nonCreatures) {
}
