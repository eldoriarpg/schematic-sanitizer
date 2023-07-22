/*
 *     SPDX-License-Identifier: LGPL-3.0-or-later
 *
 *     Copyright (C) RainbowDashLabs and Contributor
 */

package de.eldoria.schematicsanitizer.sanitizer.report.subreports;

public record ContentReport(int blocks, int nonAirBlocks, int totalCreatures, int totalNonCreatures,
                            int creatures, int nonCreatures) {
}
