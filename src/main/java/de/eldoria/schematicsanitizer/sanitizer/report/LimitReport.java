package de.eldoria.schematicsanitizer.sanitizer.report;

import de.eldoria.schematicsanitizer.sanitizer.settings.Settings;

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
}
