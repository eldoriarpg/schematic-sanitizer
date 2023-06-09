package de.eldoria.schematicsanitizer.sanitizer.report;

import de.eldoria.schematicsanitizer.sanitizer.report.entities.RemovedEntity;

import java.util.List;

public record EntityReport(List<RemovedEntity> removed) implements SizedReport<RemovedEntity> {
    @Override
    public List<RemovedEntity> entities() {
        return removed;
    }
}
