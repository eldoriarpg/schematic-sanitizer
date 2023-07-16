package de.eldoria.schematicsanitizer.sanitizer.report.builder;

import de.eldoria.schematicsanitizer.sanitizer.report.LimitReport;

/**
 * A builder class for creating LimitReport objects.
 */

public class LimitReportBuilder {
    private int maxSize;
    private final ContentReportBuilder content = new ContentReportBuilder();

    /**
     * Sets the maximum size.
     *
     * @param maxSize the maximum size to be set
     */
    public void maxSize(int maxSize) {
        this.maxSize = maxSize;
    }

    /**
     * Retrieves the ContentReportBuilder object.
     *
     * @return The ContentReportBuilder object.
     */
    public ContentReportBuilder content() {
        return content;
    }

    /**
     * Build the LimitReport object with the provided parameters.
     *
     * @return A new LimitReport object.
     */
    public LimitReport build() {
        return new LimitReport(maxSize, content.build());
    }
}
