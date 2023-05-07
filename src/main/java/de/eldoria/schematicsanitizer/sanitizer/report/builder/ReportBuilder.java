package de.eldoria.schematicsanitizer.sanitizer.report.builder;

import de.eldoria.schematicsanitizer.sanitizer.report.Report;

public class ReportBuilder {
    EntityReportBuilder entityReport =  new EntityReportBuilder();
    BlockReportBuilder blockReport =  new BlockReportBuilder();
    public Report build() {
        return new Report();
    }

    public EntityReportBuilder entity() {
        return entityReport;
    }

    public BlockReportBuilder block() {
        return null;
    }
}
