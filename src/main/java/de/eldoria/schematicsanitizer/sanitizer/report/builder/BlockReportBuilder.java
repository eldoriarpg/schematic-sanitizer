/*
 *     SPDX-License-Identifier: LGPL-3.0-or-later
 *
 *     Copyright (C) RainbowDashLabs and Contributor
 */

package de.eldoria.schematicsanitizer.sanitizer.report.builder;

import de.eldoria.schematicsanitizer.sanitizer.report.BlockReport;
import de.eldoria.schematicsanitizer.sanitizer.report.entities.RemovedBlock;

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
