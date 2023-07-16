/*
 *     SPDX-License-Identifier: LGPL-3.0-or-later
 *
 *     Copyright (C) RainbowDashLabs and Contributor
 */

package de.eldoria.schematicsanitizer.sanitizer.report;

import de.eldoria.schematicsanitizer.sanitizer.limit.ContentLimit;
import de.eldoria.schematicsanitizer.sanitizer.settings.Settings;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import static de.eldoria.schematicsanitizer.util.Text.hovered;
import static de.eldoria.schematicsanitizer.util.Text.numbers;

public record ContentReport(int blocks, int nonAirBlocks, int totalCreatures, int totalNonCreatures,
                            int creatures, int nonCreatures) {
    public String summary(Settings settings) {
        ContentLimit limit = settings.limit().contentLimit();
        return String.join(
                "\n",
                blocksLine(limit),
                nonAirBlocksLine(limit),
                totalCreaturesLine(limit),
                totalNonCreaturesLine(limit),
                creaturesLine(limit),
                nonCreaturesLine(limit)
        );
    }

    public Collection<String> errors(Settings settings) {
        List<String> errors = new LinkedList<>();
        ContentLimit limit = settings.limit().contentLimit();
        if (blocks > limit.blocks()) errors.add(blocksLine(limit));
        if (nonAirBlocks > limit.nonAirBlocks()) errors.add(nonAirBlocksLine(limit));
        if (totalCreatures > limit.creatures()) errors.add(totalCreaturesLine(limit));
        if (totalNonCreatures > limit.nonCreatures()) errors.add(totalNonCreaturesLine(limit));
        if (creatures > limit.creatures()) errors.add(creaturesLine(limit));
        if (nonCreatures > limit.nonCreatures()) errors.add(nonCreaturesLine(limit));
        return errors;
    }

    private String blocksLine(ContentLimit limit) {
        return contentLine("Blocks", "The total amount of blocks (L*W*H)", blocks, limit.blocks());
    }

    private String nonAirBlocksLine(ContentLimit limit) {
        return contentLine("Non Air Blocks", "The amount of non air blocks", nonAirBlocks, limit.nonAirBlocks());
    }

    private String totalCreaturesLine(ContentLimit limit) {
        return contentLine("Total Creatures", "The total amount of creatures. Including blacklisted", totalCreatures, limit.creatures());
    }

    private String totalNonCreaturesLine(ContentLimit limit) {
        return contentLine("Total Non Creatures", "The total amount of non creatures. Including blacklisted", totalNonCreatures, limit.nonCreatures());
    }

    private String creaturesLine(ContentLimit limit) {
        return contentLine("Creatures", "The amount of creatures that are not blacklisted", creatures, limit.creatures());
    }

    private String nonCreaturesLine(ContentLimit limit) {
        return contentLine("Non Creatures", "The amount of non creatures that are not blacklisted", nonCreatures, limit.nonCreatures());
    }

    private String contentLine(String name, String hover, int value, int limit) {
        return "<name>%s: %s".formatted(hovered(name, hover), numbers(value, limit));
    }
}
