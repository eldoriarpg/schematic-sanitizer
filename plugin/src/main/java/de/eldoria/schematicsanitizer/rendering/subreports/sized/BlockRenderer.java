/*
 *     SPDX-License-Identifier: LGPL-3.0-or-later
 *
 *     Copyright (C) RainbowDashLabs and Contributor
 */

package de.eldoria.schematicsanitizer.rendering.subreports.sized;

import de.eldoria.schematicsanitizer.rendering.entities.RemovedBlockRenderer;
import de.eldoria.schematicsanitizer.sanitizer.report.subreports.sized.BlockReport;

import java.util.List;

public record BlockRenderer(List<RemovedBlockRenderer> removed) implements SizedReportRenderer<RemovedBlockRenderer> {
    public static BlockRenderer create(BlockReport report) {
        List<RemovedBlockRenderer> entities = report.entities().stream().map(RemovedBlockRenderer::new).toList();
        return new BlockRenderer(entities);
    }

    @Override
    public int size() {
        return removed.size();
    }

    @Override
    public List<RemovedBlockRenderer> entities() {
        return removed;
    }
}
