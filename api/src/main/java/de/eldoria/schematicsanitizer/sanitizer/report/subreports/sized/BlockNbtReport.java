/*
 *     SPDX-License-Identifier: LGPL-3.0-or-later
 *
 *     Copyright (C) RainbowDashLabs and Contributor
 */

package de.eldoria.schematicsanitizer.sanitizer.report.subreports.sized;

import de.eldoria.schematicsanitizer.sanitizer.report.entities.RemovedBlockNbt;

import java.util.List;

/**
 * Removed nbt tags
 * @param removed blocks with removed tags
 */
public record BlockNbtReport(List<RemovedBlockNbt> removed) implements SizedReport<RemovedBlockNbt> {
    @Override
    public List<RemovedBlockNbt> entities() {
        return removed;
    }
}
