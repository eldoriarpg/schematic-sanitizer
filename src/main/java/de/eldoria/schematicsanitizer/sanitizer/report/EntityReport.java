/*
 *     SPDX-License-Identifier: LGPL-3.0-or-later
 *
 *     Copyright (C) RainbowDashLabs and Contributor
 */

package de.eldoria.schematicsanitizer.sanitizer.report;

import de.eldoria.schematicsanitizer.sanitizer.report.entities.RemovedEntity;

import java.util.List;

public record EntityReport(List<RemovedEntity> removed) implements SizedReport<RemovedEntity> {
    @Override
    public List<RemovedEntity> entities() {
        return removed;
    }
}
