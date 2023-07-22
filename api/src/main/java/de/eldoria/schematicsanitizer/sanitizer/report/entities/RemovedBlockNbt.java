/*
 *     SPDX-License-Identifier: LGPL-3.0-or-later
 *
 *     Copyright (C) RainbowDashLabs and Contributor
 */

package de.eldoria.schematicsanitizer.sanitizer.report.entities;

import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldedit.world.block.BlockType;
import de.eldoria.schematicsanitizer.sanitizer.report.cause.NbtRemovalCause;
import org.jetbrains.annotations.Nullable;

/**
 * Represents a removed nbt entry on a block.
 *
 * @param location     location
 * @param type         type
 * @param removalCause why it was removed
 * @param tag          removed tag
 * @param value        removed value if it contained invalid text
 */
public record RemovedBlockNbt(BlockVector3 location, BlockType type, NbtRemovalCause removalCause, String tag,
                              @Nullable String value) {
}
