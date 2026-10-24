/*
 *     SPDX-License-Identifier: LGPL-3.0-or-later
 *
 *     Copyright (C) EldoriaRPG Team and Contributor
 */
package de.eldoria.schematicsanitizer.sanitizer.report.builder;

import de.eldoria.schematicsanitizer.sanitizer.report.entities.RemovedBlock;
import de.eldoria.schematicsanitizer.sanitizer.report.subreports.sized.BlockReport;

/**
 * A class for building BlockReport objects.
 * Extends the BaseReportBuilder class and specifies the RemovedBlock type.
 */
public class BlockReportBuilder extends BaseReportBuilder<RemovedBlock> {

    /**
     * Builds a BlockReport object by calling the "removed()" method and creating a new instance of BlockReport.
     *
     * @return The new BlockReport object.
     */
    public BlockReport build() {
        return new BlockReport(removed());
    }
}
