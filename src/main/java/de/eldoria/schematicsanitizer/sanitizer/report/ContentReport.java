/*
 *     SPDX-License-Identifier: LGPL-3.0-or-later
 *
 *     Copyright (C) RainbowDashLabs and Contributor
 */

package de.eldoria.schematicsanitizer.sanitizer.report;

import de.eldoria.schematicsanitizer.sanitizer.limit.ContentLimit;
import de.eldoria.schematicsanitizer.sanitizer.settings.Settings;

import static de.eldoria.schematicsanitizer.util.Text.hovered;
import static de.eldoria.schematicsanitizer.util.Text.numbers;

public record ContentReport(int blocks, int nonAirBlocks, int totalCreatures, int totalNonCreatures,
                            int creatures, int nonCreatures) {
    public String summary(Settings settings) {
        ContentLimit contentLimit = settings.limit().contentLimit();
        return """
                <name>%s: %s
                <name>%s: %s
                <name>%s: %s
                <name>%s: %s
                <name>%s: %s
                <name>%s: %s""".formatted(
                hovered("Blocks", "The total amount of blocks (L*W*H)"), numbers(blocks, contentLimit.blocks()),
                hovered("Non Air Blocks", "The amount of non air blocks"), numbers(nonAirBlocks, contentLimit.nonAirBlocks()),
                hovered("Total Creatures", "The total amount of creatures. Including blacklisted"), numbers(totalCreatures, contentLimit.creatures()),
                hovered("Total Non Creatures", "The total amount of non creatures. Including blacklisted"), numbers(totalNonCreatures, contentLimit.nonCreatures()),
                hovered("Creatures", "The amount of creatures that are not blacklisted"), numbers(creatures, contentLimit.creatures()),
                hovered("Non Creatures", "The amount of non creatures that are not blacklisted"), numbers(nonCreatures, contentLimit.nonCreatures()));
    }
}
