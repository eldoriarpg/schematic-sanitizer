/*
 *     SPDX-License-Identifier: AGPL-3.0-only
 *
 *     Copyright (C) EldoriaRPG Team and Contributor
 */
package de.eldoria.schematicsanitizer.util;

import de.eldoria.schematicsanitizer.sanitizer.report.subreports.sized.SizedReport;
import net.kyori.adventure.text.minimessage.MiniMessage;

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

    /**
     * Escapes text that comes from a schematic or a file name, so MiniMessage shows it as it is instead of reading
     * tags from it.
     *
     * @param text untrusted text
     * @return the text with every tag escaped
     */
    public static String escape(String text) {
        return MiniMessage.miniMessage().escapeTags(text);
    }

    /**
     * Prepares text for a single-quoted tag argument, such as the command of a click or the text of a hover. Quotes
     * and backslashes are escaped so the text cannot end the argument.
     *
     * @param text untrusted text
     * @return the text, safe inside {@code '…'}
     */
    public static String quoted(String text) {
        return text.replace("\\", "\\\\").replace("'", "\\'");
    }
}
