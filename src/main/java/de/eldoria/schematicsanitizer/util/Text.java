/*
 *     SPDX-License-Identifier: LGPL-3.0-or-later
 *
 *     Copyright (C) RainbowDashLabs and Contributor
 */

package de.eldoria.schematicsanitizer.util;

import de.eldoria.schematicsanitizer.sanitizer.report.SizedReport;

public class Text {
    /**
     * Formats the given value and maximum value into a string with color limit.
     *
     * @param value the value to compare
     * @param max   the maximum value to compare against
     * @return the formatted string with color limit
     */
    public static String numbers(int value, int max) {
        return "%s%,d/%,d".formatted(limitColor(value, max), value, max);
    }

    /**
     * Determines the color limit based on a given value and maximum value.
     *
     * @param value the value to compare
     * @param max   the maximum value to compare against
     * @return the color limit ({@code <good>} or {@code <bad>})
     */
    public static String limitColor(int value, int max) {
        return value <= max ? "<good>" : "<bad>";
    }

    /**
     * Formats the size of a report and returns it in a specific format.
     *
     * @param report the SizedReport object to get the size from
     * @return a formatted string representing the size of the report
     */
    public static String numbers(SizedReport<?> report) {
        return "%s%,d".formatted(report.isEmpty() ? "<good>" : "<bad>", report.size());
    }

    /**
     * Formats a given text and adds hover functionality.
     * The text will be wrapped with hover tags to show the hoverText when hovered.
     *
     * @param text      the original text to format
     * @param hoverText the text to be shown as hover when text is hovered
     * @return the formatted text with hover functionality
     */
    public static String hovered(String text, String hoverText) {
        return "<hover:show_text:'<default>%s'>%s</hover>".formatted(hoverText, text);
    }

}
