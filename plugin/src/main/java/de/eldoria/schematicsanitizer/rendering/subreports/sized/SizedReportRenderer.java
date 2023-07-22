/*
 *     SPDX-License-Identifier: LGPL-3.0-or-later
 *
 *     Copyright (C) RainbowDashLabs and Contributor
 */

package de.eldoria.schematicsanitizer.rendering.subreports.sized;

import de.eldoria.schematicsanitizer.rendering.entities.ComponentEntityRenderer;
import de.eldoria.schematicsanitizer.sanitizer.report.subreports.sized.SizedReport;

import java.util.List;
import java.util.stream.Collectors;

public interface SizedReportRenderer<T extends ComponentEntityRenderer> extends SizedReport<T> {
    /**
     * Returns a paged sublist of entities.
     *
     * @param page the page number
     * @param size the page size
     * @return a sublist of entities based on the given page and size
     */
    default List<T> page(int page, int size) {
        return entities().subList(Math.min(page * size, size() - 1), Math.min(size(), page * size + size));
    }

    /**
     * Returns the total number of pages.
     *
     * @param size the page size
     * @return the total number of pages
     */
    default int pages(int size) {
        return (int) Math.ceil(entities().size() / (double) size);
    }

    /**
     * Returns the page component for the given page and size.
     *
     * @param page the page number
     * @param size the page size
     * @return the page component
     */
    default String pageComponent(int page, int size) {
        return page(page, size).stream().map(ComponentEntityRenderer::component).collect(Collectors.joining("\n"));
    }
}
