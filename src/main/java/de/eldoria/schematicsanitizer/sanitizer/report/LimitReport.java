package de.eldoria.schematicsanitizer.sanitizer.report;

import de.eldoria.schematicsanitizer.sanitizer.settings.Settings;

import static de.eldoria.schematicsanitizer.util.Text.numbers;

public record LimitReport(int maxSize, ContentReport content) {
    public String summary(Settings settings) {
        return """
                <gold>Limits:<default>
                  Max Size: %s
                <gold>Content:<default>
                %s""".formatted(numbers(maxSize, settings.limit().size()), content.summary(settings).indent(2));
    }
}
