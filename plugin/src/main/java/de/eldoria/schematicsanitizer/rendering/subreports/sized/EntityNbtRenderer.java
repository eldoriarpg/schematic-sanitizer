/*
 *     SPDX-License-Identifier: LGPL-3.0-or-later
 *
 *     Copyright (C) RainbowDashLabs and Contributor
 */

package de.eldoria.schematicsanitizer.rendering.subreports.sized;

import de.eldoria.schematicsanitizer.rendering.entities.RemovedEntityNbtRenderer;
import de.eldoria.schematicsanitizer.sanitizer.report.subreports.sized.EntityNbtReport;

import java.util.List;

public record EntityNbtRenderer(
        List<RemovedEntityNbtRenderer> removed) implements SizedReportRenderer<RemovedEntityNbtRenderer> {
    public static EntityNbtRenderer create(EntityNbtReport report) {
        List<RemovedEntityNbtRenderer> entities = report.entities().stream().map(RemovedEntityNbtRenderer::new).toList();
        return new EntityNbtRenderer(entities);
    }

    @Override
    public List<RemovedEntityNbtRenderer> entities() {
        return removed;
    }

}
