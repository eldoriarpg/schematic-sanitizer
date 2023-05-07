package de.eldoria.schematicsanitizer.sanitizer.report.builder;

import java.util.ArrayList;
import java.util.List;

public class EntityReportBuilder {
    private final List<RemovedEntity> removed = new ArrayList<>();


    public void removed(RemovedEntity removedEntity) {
        this.removed.add(removedEntity);
    }
}
