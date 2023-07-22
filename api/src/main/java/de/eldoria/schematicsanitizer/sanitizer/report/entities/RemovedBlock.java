/*
 *     SPDX-License-Identifier: LGPL-3.0-or-later
 *
 *     Copyright (C) RainbowDashLabs and Contributor
 */

package de.eldoria.schematicsanitizer.sanitizer.report.entities;

import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldedit.world.block.BlockType;
import de.eldoria.schematicsanitizer.sanitizer.report.cause.BlockRemovalCause;

/**
 * Represents a removed block.
 *
 * @param location     block location
 * @param type         block type
 * @param removalCause reason why it was removed
 */
public record RemovedBlock(BlockVector3 location,
                           BlockType type,
                           BlockRemovalCause removalCause) {
}
