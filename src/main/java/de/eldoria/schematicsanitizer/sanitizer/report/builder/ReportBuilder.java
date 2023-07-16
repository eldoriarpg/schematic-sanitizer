package de.eldoria.schematicsanitizer.sanitizer.report.builder;

import de.eldoria.schematicsanitizer.sanitizer.report.SanitizerReport;

import java.nio.file.Path;

/**
 * The ReportBuilder class is responsible for building a ComponentSanitizerReport object.
 * It provides methods to access and configure different types of report builders such as EntityReportBuilder,
 * BlockReportBuilder, BlockNbtReportBuilder, EntityNbtReportBuilder, and LimitReportBuilder.
 * It also provides a method to set a new path and a method to build the final ComponentSanitizerReport object.
 */

public class ReportBuilder {
    private final EntityReportBuilder entityReport = new EntityReportBuilder();
    private final BlockReportBuilder blockReport = new BlockReportBuilder();
    private final BlockNbtReportBuilder blockNbtReport = new BlockNbtReportBuilder();
    private final EntityNbtReportBuilder entityNbtReport = new EntityNbtReportBuilder();
    private final LimitReportBuilder limitReportBuilder = new LimitReportBuilder();
    private Path newPath = null;

    /**
     * Retrieves the EntityReportBuilder instance.
     *
     * @return The EntityReportBuilder instance.
     */
    public EntityReportBuilder entity() {
        return entityReport;
    }

    /**
     * Returns the BlockReportBuilder object.
     *
     * @return The BlockReportBuilder instance.
     */
    public BlockReportBuilder block() {
        return blockReport;
    }

    /**
     * Retrieves the BlockNbtReportBuilder instance.
     *
     * @return The BlockNbtReportBuilder instance.
     */
    public BlockNbtReportBuilder blockNbt() {
        return blockNbtReport;
    }

    /**
     * Retrieves the EntityNbtReportBuilder instance.
     *
     * @return The EntityNbtReportBuilder instance.
     */
    public EntityNbtReportBuilder entityNbt() {
        return entityNbtReport;
    }

    /**
     * Retrieves the LimitReportBuilder instance.
     *
     * @return The LimitReportBuilder instance.
     */
    public LimitReportBuilder limit() {
        return limitReportBuilder;
    }

    /**
     * Sets the new path.
     *
     * @param newPath The new path to set.
     */
    public void newPath(Path newPath) {
        this.newPath = newPath;
    }

    public SanitizerReport build() {
        return new SanitizerReport(newPath, entityReport.build(), blockReport.build(), entityNbtReport.build(), blockNbtReport.build(), limitReportBuilder.build());
    }
}
