/*
 *     SPDX-License-Identifier: LGPL-3.0-or-later
 *
 *     Copyright (C) RainbowDashLabs and Contributor
 */

package de.eldoria.schematicsanitizer.sanitizer.report.subreports.sized;

import de.eldoria.schematicsanitizer.sanitizer.report.entities.RemovedEntityNbt;

import java.util.List;

/**
 * Removed nbt tags
 * @param removed entities with removed tags
 */
public record EntityNbtReport(List<RemovedEntityNbt> removed) implements SizedReport<RemovedEntityNbt> {
    @Override
    public List<RemovedEntityNbt> entities() {
        return removed;
    }

}
