package de.eldoria.schematicsanitizer.sanitizer.report.builder;

import de.eldoria.schematicsanitizer.sanitizer.report.LimitReport;

public class LimitReportBuilder {
    private int maxSize;
    private final ContentReportBuilder content = new ContentReportBuilder();

    public void maxSize(int maxSize) {
        this.maxSize = maxSize;
    }

    public ContentReportBuilder content() {
        return content;
    }

    public LimitReport build() {
        return new LimitReport(maxSize, content.build());
    }
}
