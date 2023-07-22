/*
 *     SPDX-License-Identifier: LGPL-3.0-or-later
 *
 *     Copyright (C) RainbowDashLabs and Contributor
 */

package de.eldoria.schematicsanitizer.rendering.subreports;

import de.eldoria.schematicsanitizer.sanitizer.report.subreports.LimitReport;
import de.eldoria.schematicsanitizer.sanitizer.settings.Settings;
import de.eldoria.schematicsanitizer.util.Text;

import java.util.LinkedList;
import java.util.List;

import static de.eldoria.schematicsanitizer.util.Text.numbers;

public record LimitRenderer(LimitReport report, ContentRenderer content) {
    public static LimitRenderer create(LimitReport limit) {
        return new LimitRenderer(limit, ContentRenderer.create(limit.content()));
    }

    public String summary(Settings settings) {
        return """
                <section>Limits:<default>
                  <name>%s: %s
                                
                <section>Content:<default>
                %s""".formatted(
                Text.hovered("Max Size", "The longest side of the schematic"),
                numbers(maxSize(), settings.limit().size()),
                content().summary(settings).indent(2));
    }

    public List<String> errors(Settings settings) {
        List<String> entities = new LinkedList<>();
        if (maxSize() > settings.limit().size()) {
            entities.add("<name>%s: %s".formatted(Text.hovered("Max Size", "The longest side of the schematic"), numbers(maxSize(), settings.limit().size())));
        }
        entities.addAll(content().errors(settings));
        return entities;
    }

    public int maxSize() {
        return report.maxSize();
    }
}
