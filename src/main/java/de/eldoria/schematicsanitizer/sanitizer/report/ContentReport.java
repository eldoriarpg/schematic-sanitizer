package de.eldoria.schematicsanitizer.sanitizer.report;

import de.eldoria.schematicsanitizer.sanitizer.limit.ContentLimit;
import de.eldoria.schematicsanitizer.sanitizer.settings.Settings;

import static de.eldoria.schematicsanitizer.util.Text.numbers;

public record ContentReport(int blocks, int nonAirBlocks, int totalCreatures, int totalNonCreatures,
                            int creatures, int nonCreatures) {
    public String summary(Settings settings) {
        ContentLimit contentLimit = settings.limit().contentLimit();
        return """
                <default>Blocks: %s
                <default>Non Air Blocks: %s
                <default>Total Creatures: %s
                <default>Total Non Creatures: %s
                <default>Creatures: %s
                <default>Non Creatures: %s""".formatted(numbers(blocks, contentLimit.blocks()),
                numbers(nonAirBlocks, contentLimit.nonAirBlocks()),
                numbers(totalCreatures, contentLimit.creatures()),
                numbers(totalNonCreatures, contentLimit.nonCreatures()),
                numbers(creatures, contentLimit.creatures()),
                numbers(nonCreatures, contentLimit.nonCreatures()));
    }
}
