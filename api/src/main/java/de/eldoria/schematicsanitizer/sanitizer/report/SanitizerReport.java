/*
 *     SPDX-License-Identifier: LGPL-3.0-or-later
 *
 *     Copyright (C) RainbowDashLabs and Contributor
 */

package de.eldoria.schematicsanitizer.sanitizer.report;

import de.eldoria.schematicsanitizer.sanitizer.Sanitizer;
import de.eldoria.schematicsanitizer.sanitizer.report.subreports.LimitReport;
import de.eldoria.schematicsanitizer.sanitizer.report.subreports.sized.BlockNbtReport;
import de.eldoria.schematicsanitizer.sanitizer.report.subreports.sized.BlockReport;
import de.eldoria.schematicsanitizer.sanitizer.report.subreports.sized.EntityNbtReport;
import de.eldoria.schematicsanitizer.sanitizer.report.subreports.sized.EntityReport;
import org.jetbrains.annotations.Nullable;

import java.nio.file.Path;

/**
 * A report representing actions taken by a {@link Sanitizer} process.
 *
 * @param newPath     the new path of the file if one was created
 * @param entities    the removed entities because of their type
 * @param blocks      the removed blocks because of their type
 * @param entitiesNbt the removed entities because of their tags
 * @param blocksNbt   the removed blocks because of their tags
 * @param limits      the limits or the schematics
 */
public record SanitizerReport(Path path, @Nullable Path newPath, EntityReport entities, BlockReport blocks,
                              EntityNbtReport entitiesNbt, BlockNbtReport blocksNbt, LimitReport limits) {
}
