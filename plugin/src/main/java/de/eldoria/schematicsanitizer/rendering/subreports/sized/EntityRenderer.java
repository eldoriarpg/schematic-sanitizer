/*
 *     SPDX-License-Identifier: LGPL-3.0-or-later
 *
 *     Copyright (C) RainbowDashLabs and Contributor
 */

package de.eldoria.schematicsanitizer.rendering.subreports.sized;

import de.eldoria.schematicsanitizer.rendering.entities.RemovedEntityRenderer;
import de.eldoria.schematicsanitizer.sanitizer.report.subreports.sized.EntityReport;

import java.util.List;

public record EntityRenderer(
        List<RemovedEntityRenderer> removed) implements SizedReportRenderer<RemovedEntityRenderer> {
    public static EntityRenderer create(EntityReport report) {
        List<RemovedEntityRenderer> entities = report.entities().stream().map(RemovedEntityRenderer::new).toList();
        return new EntityRenderer(entities);
    }

    @Override
    public List<RemovedEntityRenderer> entities() {
        return removed;
    }
}
