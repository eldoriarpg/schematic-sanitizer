package de.eldoria.schematicsanitizer.sanitizer.report.builder;

import java.util.ArrayList;
import java.util.List;

public class BlockReportBuilder {
    private final List<RemovedBlock> removed = new ArrayList<>();


    public void removed(RemovedBlock removedEntity) {
        this.removed.add(removedEntity);
    }
}
