/*
 *     SPDX-License-Identifier: LGPL-3.0-or-later
 *
 *     Copyright (C) RainbowDashLabs and Contributor
 */

package de.eldoria.schematicsanitizer.sanitizer.filter.builder;

import de.eldoria.schematicsanitizer.sanitizer.filter.Filter;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.function.Consumer;

/**
 * The FilterBuilder class is responsible for creating and configuring filters for block and entity data.
 * It allows for the creation of a filter with a block filter, entity filter, text blacklist, and NBT(blacklist).
 */

public class FilterBuilder {
    private final BlockFilterBuilder blockFilter = new BlockFilterBuilder();
    private final EntityFilterBuilder entityFilter = new EntityFilterBuilder();
    private final Set<String> textBlacklist = new HashSet<>();
    private final Set<String> nbtBlacklist = new HashSet<>();

    /**
     * Applies the provided block filter to the existing filter configuration.
     *
     * @param filter A consumer functional interface that accepts a BlockFilterBuilder
     *               for configuring the block filter.
     * @return The current instance of FilterBuilder with the block filter applied.
     */
    public FilterBuilder blockFilter(Consumer<BlockFilterBuilder> filter) {
        filter.accept(blockFilter);
        return this;
    }

    /**
     * Applies a filter to the entity using a {@code Consumer}.
     *
     * @param filter the filter to be applied to the entity
     * @return the FilterBuilder instance
     */
    public FilterBuilder entityFilter(Consumer<EntityFilterBuilder> filter) {
        filter.accept(entityFilter);
        return this;
    }


    /**
     * Sets the text blacklist for filtering.
     *
     * @param text The list of text to be blacklisted.
     * @return A new FilterBuilder object with the updated text blacklist.
     */
    public FilterBuilder withTextBlacklist(String... text) {
        return withTextBlacklist(Arrays.stream(text).filter(Objects::nonNull).toList());
    }

    /**
     * Add a list of text to the blacklist for filtering.
     *
     * @param text A List of String objects representing the text to be blacklisted.
     * @return The FilterBuilder instance with the blacklist updated.
     */
    public FilterBuilder withTextBlacklist(List<String> text) {
        textBlacklist.addAll(text);
        return this;
    }

    /**
     * Sets the NBT blacklist for the filter.
     *
     * @param text the array of NBT blacklist entries
     * @return the updated FilterBuilder object
     */
    public FilterBuilder withNbtBlacklist(String... text) {
        return withNbtBlacklist(Arrays.stream(text).filter(Objects::nonNull).toList());
    }

    /**
     * Adds the specified list of strings to the NBT (Named Binary Tag) blacklist.
     * Any item with NBT matching the strings in the blacklist will be excluded by the filter.
     *
     * @param text the list of strings to add to the NBT blacklist
     * @return a reference to this FilterBuilder for method chaining
     */
    public FilterBuilder withNbtBlacklist(List<String> text) {
        nbtBlacklist.addAll(text);
        return this;
    }

    /**
     * Builds the Filter object using the specified parameters.
     *
     * @return the built Filter object.
     */
    public Filter build() {
        return new Filter(blockFilter.build(), entityFilter.build(), textBlacklist, nbtBlacklist);
    }


}
