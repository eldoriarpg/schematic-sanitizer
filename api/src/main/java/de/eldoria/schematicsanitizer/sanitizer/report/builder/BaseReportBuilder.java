/*
 *     SPDX-License-Identifier: LGPL-3.0-or-later
 *
 *     Copyright (C) RainbowDashLabs and Contributor
 */

package de.eldoria.schematicsanitizer.sanitizer.report.builder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * A generic base class for report builders.
 *
 * @param <V> the type of the elements in the report
 */

public class BaseReportBuilder<V> {
    private final List<V> removed = new ArrayList<>();

    /**
     * Adds the specified element to the removed collection.
     *
     * @param removed the element to be added to the removed collection
     */
    public void removed(V removed) {
        this.removed.add(removed);
    }

    /**
     * Returns an unmodifiable view of the removed collection.
     *
     * @return an unmodifiable view of the removed collection
     */
    public List<V> removed() {
        return Collections.unmodifiableList(removed);
    }
}
