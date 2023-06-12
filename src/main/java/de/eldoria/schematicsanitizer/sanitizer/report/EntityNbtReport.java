package de.eldoria.schematicsanitizer.sanitizer.report;

import de.eldoria.schematicsanitizer.sanitizer.report.entities.RemovedEntityNbt;

import java.util.List;

public record EntityNbtReport(List<RemovedEntityNbt> removed) implements SizedReport<RemovedEntityNbt> {
    @Override
    public List<RemovedEntityNbt> entities() {
        return removed;
    }

}
