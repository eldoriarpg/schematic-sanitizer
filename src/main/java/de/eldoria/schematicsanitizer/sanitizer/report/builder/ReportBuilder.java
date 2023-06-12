package de.eldoria.schematicsanitizer.sanitizer.report.builder;

import de.eldoria.schematicsanitizer.sanitizer.report.SanitizerReport;

public class ReportBuilder {
    private final EntityReportBuilder entityReport = new EntityReportBuilder();
    private final BlockReportBuilder blockReport = new BlockReportBuilder();
    private final BlockNbtReportBuilder blockNbtReport = new BlockNbtReportBuilder();
    private final EntityNbtReportBuilder entityNbtReport = new EntityNbtReportBuilder();
    private final LimitReportBuilder limitReportBuilder = new LimitReportBuilder();

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

    public LimitReportBuilder limit(){
        return limitReportBuilder;
    }

    public SanitizerReport build() {
        return new SanitizerReport(entityReport.build(), blockReport.build(), entityNbtReport.build(), blockNbtReport.build(), limitReportBuilder.build());
    }
}
