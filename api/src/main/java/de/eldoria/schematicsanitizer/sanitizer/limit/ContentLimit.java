/*
 *     SPDX-License-Identifier: LGPL-3.0-or-later
 *
 *     Copyright (C) RainbowDashLabs and Contributor
 */

package de.eldoria.schematicsanitizer.sanitizer.limit;

public record ContentLimit(int blocks, int nonAirBlocks, int creatures,
                           int nonCreatures) {
}
