/*
 *     SPDX-License-Identifier: LGPL-3.0-or-later
 *
 *     Copyright (C) RainbowDashLabs and Contributor
 */

package de.eldoria.schematicsanitizer.sanitizer.filter;

import java.util.Set;

public record Filter(BlockFilter blockFilter, EntityFilter entityFilter, Set<String> textBlacklist,
                     Set<String> nbtBlacklist) {

}
