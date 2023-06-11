package de.eldoria.schematicsanitizer.sanitizer.report.builder;

import de.eldoria.schematicsanitizer.sanitizer.report.Report;

public class ReportBuilder {
    private final EntityReportBuilder entityReport = new EntityReportBuilder();
    private final BlockReportBuilder blockReport = new BlockReportBuilder();
    private final BlockNbtReportBuilder blockNbtReport = new BlockNbtReportBuilder();
    private final EntityNbtReportBuilder entityNbtReport = new EntityNbtReportBuilder();

    public EntityReportBuilder entity() {
        return entityReport;
    }

    public BlockReportBuilder block() {
        return blockReport;
    }

    public BlockNbtReportBuilder blockNbt() {
        return blockNbtReport;
    }

    public EntityNbtReportBuilder entityNbt() {
        return entityNbtReport;
    }

    public Report build() {
        return new Report(entityReport.build(), blockReport.build(), entityNbtReport.build(), blockNbtReport.build());
    }
}
