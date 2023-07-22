/*
 *     SPDX-License-Identifier: LGPL-3.0-or-later
 *
 *     Copyright (C) RainbowDashLabs and Contributor
 */

package de.eldoria.schematicsanitizer.sanitizer.filter;

import java.util.Set;

/**
 * A filter for a schematic
 *
 * @param blockFilter   block filter
 * @param entityFilter  entity filter
 * @param textBlacklist blacklist for values of text nbt tags
 * @param nbtBlacklist  blacklist for nbt keys
 */
public record Filter(BlockFilter blockFilter,
                     EntityFilter entityFilter,
                     Set<String> textBlacklist,
                     Set<String> nbtBlacklist) {

}
