package de.eldoria.schematicsanitizer.sanitizer.report.builder;

import de.eldoria.schematicsanitizer.sanitizer.report.EntityReport;
import de.eldoria.schematicsanitizer.sanitizer.report.entities.RemovedEntity;

public class EntityReportBuilder extends BaseReportBuilder<RemovedEntity> {
    public EntityReport build() {
        return new EntityReport(removed());
    }
}
