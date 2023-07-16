/*
 *     SPDX-License-Identifier: LGPL-3.0-or-later
 *
 *     Copyright (C) RainbowDashLabs and Contributor
 */

package de.eldoria.schematicsanitizer.sanitizer.settings.builder;

import de.eldoria.schematicsanitizer.sanitizer.filter.builder.FilterBuilder;
import de.eldoria.schematicsanitizer.sanitizer.limit.builder.LimitBuilder;
import de.eldoria.schematicsanitizer.sanitizer.settings.Settings;

import java.util.function.Consumer;

public class SettingsBuilder {
    private final FilterBuilder filter = new FilterBuilder();
    private final LimitBuilder limit = new LimitBuilder();

    /**
     * Applies the given filter to the SettingsBuilder.
     *
     * @param filter A Consumer that accepts a FilterBuilder and applies the filter to it.
     * @return The modified SettingsBuilder instance.
     */
    public SettingsBuilder filter(Consumer<FilterBuilder> filter) {
        filter.accept(this.filter);
        return this;
    }

    /**
     * Sets the limit for a specific setting using a consumer function.
     *
     * @param limit The consumer function that accepts a LimitBuilder instance for configuring the limit.
     * @return The SettingsBuilder instance with the updated limit.
     */
    public SettingsBuilder limit(Consumer<LimitBuilder> limit) {
        limit.accept(this.limit);
        return this;
    }

    /**
     * Builds and returns a {@link Settings} object.
     *
     * @return The built {@link Settings} object.
     */
    public Settings build() {
        return new Settings(filter.build(), limit.build());
    }
}
