package de.eldoria.schematicsanitizer.sanitizer.report.builder;

import de.eldoria.schematicsanitizer.sanitizer.report.BlockReport;
import de.eldoria.schematicsanitizer.sanitizer.report.entities.RemovedBlock;

public class BlockReportBuilder extends BaseReportBuilder<RemovedBlock> {

    public BlockReport build() {
        return new BlockReport(removed());
    }
}
