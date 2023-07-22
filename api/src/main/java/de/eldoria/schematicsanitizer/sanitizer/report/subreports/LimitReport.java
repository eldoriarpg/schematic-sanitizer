/*
 *     SPDX-License-Identifier: LGPL-3.0-or-later
 *
 *     Copyright (C) RainbowDashLabs and Contributor
 */

package de.eldoria.schematicsanitizer.sanitizer.report.subreports;

/**
 * Report about limits
 * @param maxSize the longest edge of the region
 * @param content content report
 */
public record LimitReport(int maxSize, ContentReport content) {
}
