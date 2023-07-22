/*
 *     SPDX-License-Identifier: LGPL-3.0-or-later
 *
 *     Copyright (C) RainbowDashLabs and Contributor
 */

package de.eldoria.schematicsanitizer.sanitizer.report.builder;

import de.eldoria.schematicsanitizer.sanitizer.report.subreports.sized.EntityReport;
import de.eldoria.schematicsanitizer.sanitizer.report.entities.RemovedEntity;

/**
 * A class for building entity reports.
 * Extends the BaseReportBuilder class to inherit its functionality.
 */

public class EntityReportBuilder extends BaseReportBuilder<RemovedEntity> {
    /**
     * Builds an instance of EntityReport.
     *
     * @return An instance of EntityReport with the result of removed() method as input.
     */
    public EntityReport build() {
        return new EntityReport(removed());
    }
}
