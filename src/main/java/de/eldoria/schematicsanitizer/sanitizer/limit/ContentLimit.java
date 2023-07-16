/*
 *     SPDX-License-Identifier: LGPL-3.0-or-later
 *
 *     Copyright (C) RainbowDashLabs and Contributor
 */

package de.eldoria.schematicsanitizer.sanitizer.limit;

import com.fasterxml.jackson.annotation.JsonProperty;

public record ContentLimit(int blocks, @JsonProperty(defaultValue = "1000") int nonAirBlocks, int creatures,
                           int nonCreatures) {
}
