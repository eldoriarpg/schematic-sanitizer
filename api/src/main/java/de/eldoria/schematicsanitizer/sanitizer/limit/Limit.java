/*
 *     SPDX-License-Identifier: LGPL-3.0-or-later
 *
 *     Copyright (C) RainbowDashLabs and Contributor
 */

package de.eldoria.schematicsanitizer.sanitizer.limit;

/**
 * The limits for a schematic
 * @param size the max length of the longest edge
 * @param contentLimit content limit
 */
public record Limit(int size, ContentLimit contentLimit) {
}
