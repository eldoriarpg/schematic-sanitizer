package de.eldoria.schematicsanitizer.sanitizer.report;

import de.eldoria.schematicsanitizer.sanitizer.report.entities.RemovedBlock;

import java.util.List;

public record BlockReport(List<RemovedBlock> removed) implements SizedReport<RemovedBlock> {
    @Override
    public int size() {
        return removed.size();
    }

    @Override
    public List<RemovedBlock> entities() {
        return removed;
    }
}
