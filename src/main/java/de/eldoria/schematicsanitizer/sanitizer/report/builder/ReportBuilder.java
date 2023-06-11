package de.eldoria.schematicsanitizer.sanitizer.report.builder;

import de.eldoria.schematicsanitizer.sanitizer.report.Report;

public class ReportBuilder {
    EntityReportBuilder entityReport =  new EntityReportBuilder();
    BlockReportBuilder blockReport =  new BlockReportBuilder();
    BlockNbtReportBuilder blockNbtReport =  new BlockNbtReportBuilder();
    EntityNbtReportBuilder entityNbtReport =  new EntityNbtReportBuilder();

    public Report build() {
        return new Report();
    }

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
}
