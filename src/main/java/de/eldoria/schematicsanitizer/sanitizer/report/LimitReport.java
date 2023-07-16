/*
 *     SPDX-License-Identifier: LGPL-3.0-or-later
 *
 *     Copyright (C) RainbowDashLabs and Contributor
 */

package de.eldoria.schematicsanitizer.sanitizer.report;

import de.eldoria.schematicsanitizer.sanitizer.settings.Settings;

import java.util.LinkedList;
import java.util.List;

import static de.eldoria.schematicsanitizer.util.Text.hovered;
import static de.eldoria.schematicsanitizer.util.Text.numbers;

public record LimitReport(int maxSize, ContentReport content) {
    public String summary(Settings settings) {
        return """
                <section>Limits:<default>
                  <name>%s: %s
                                
                <section>Content:<default>
                %s""".formatted(
                hovered("Max Size", "The longest side of the schematic"),
                numbers(maxSize, settings.limit().size()),
                content.summary(settings).indent(2));
    }

    public List<String> errors(Settings settings) {
        List<String> entities = new LinkedList<>();
        if (maxSize > settings.limit().size()) {
            entities.add("<name>%s: %s".formatted(hovered("Max Size", "The longest side of the schematic"), numbers(maxSize, settings.limit().size())));
        }
        entities.addAll(content.errors(settings));
        return entities;
    }
}
