/*
 *     SPDX-License-Identifier: LGPL-3.0-or-later
 *
 *     Copyright (C) RainbowDashLabs and Contributor
 */

package de.eldoria.schematicsanitizer.sanitizer.report.subreports.sized;


import java.util.List;

/**
 * Represents a sized report
 * @param <T> type of the report elements
 */
public interface SizedReport<T> {
    /**
     * Returns the size of the list of entities.
     *
     * @return the size of the list of entities
     */
    default int size() {
        return entities().size();
    }

    /**
     * Returns true if the list of entities is empty, false otherwise.
     *
     * @return true if the list of entities is empty, false otherwise
     */
    default boolean isEmpty() {
        return entities().isEmpty();
    }

    /**
     * Returns a list of entities.
     *
     * @return a list of entities
     */
    List<T> entities();
}
