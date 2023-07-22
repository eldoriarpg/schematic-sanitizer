/*
 *     SPDX-License-Identifier: LGPL-3.0-or-later
 *
 *     Copyright (C) RainbowDashLabs and Contributor
 */

package de.eldoria.schematicsanitizer.rendering.subreports.sized;

import de.eldoria.schematicsanitizer.rendering.entities.RemovedBlockNbtRenderer;
import de.eldoria.schematicsanitizer.sanitizer.report.subreports.sized.BlockNbtReport;

import java.util.List;

public record BlockNbtRenderer(
        List<RemovedBlockNbtRenderer> removed) implements SizedReportRenderer<RemovedBlockNbtRenderer> {
    public static BlockNbtRenderer create(BlockNbtReport report) {
        List<RemovedBlockNbtRenderer> entities = report.entities().stream().map(RemovedBlockNbtRenderer::new).toList();
        return new BlockNbtRenderer(entities);
    }

    @Override
    public List<RemovedBlockNbtRenderer> entities() {
        return removed;
    }
}
