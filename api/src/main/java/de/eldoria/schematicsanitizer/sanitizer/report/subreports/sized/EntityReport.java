/*
 *     SPDX-License-Identifier: LGPL-3.0-or-later
 *
 *     Copyright (C) RainbowDashLabs and Contributor
 */

package de.eldoria.schematicsanitizer.sanitizer.report.subreports.sized;

import de.eldoria.schematicsanitizer.sanitizer.report.entities.RemovedEntity;

import java.util.List;

/**
 * Removed entities
 *
 * @param removed entities
 */
public record EntityReport(List<RemovedEntity> removed) implements SizedReport<RemovedEntity> {
    @Override
    public List<RemovedEntity> entities() {
        return removed;
    }
}
