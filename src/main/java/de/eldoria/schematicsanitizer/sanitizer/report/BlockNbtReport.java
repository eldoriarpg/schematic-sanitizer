package de.eldoria.schematicsanitizer.sanitizer.report;

import de.eldoria.schematicsanitizer.sanitizer.report.entities.RemovedBlockNbt;

import java.util.List;

public record BlockNbtReport(List<RemovedBlockNbt> removed) implements SizedReport<RemovedBlockNbt> {
    @Override
    public List<RemovedBlockNbt> entities() {
        return removed;
    }
}
