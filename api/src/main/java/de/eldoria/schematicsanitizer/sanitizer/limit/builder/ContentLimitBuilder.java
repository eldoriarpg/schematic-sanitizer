/*
 *     SPDX-License-Identifier: LGPL-3.0-or-later
 *
 *     Copyright (C) RainbowDashLabs and Contributor
 */

package de.eldoria.schematicsanitizer.sanitizer.limit.builder;

import de.eldoria.schematicsanitizer.sanitizer.limit.ContentLimit;

/**
 * The ContentLimitBuilder class is used to construct {@link ContentLimit} objects.
 * It provides methods to set the limits for blocks, non-air blocks, creatures, and non-creatures.
 * Once the limits are set, the build() method can be called to create a ContentLimit object.
 */

public class ContentLimitBuilder {
    private int blocks = 50000;
    private int nonAirBlocks = 50000;
    private int creatures = 50;
    private int nonCreatures = 600;

    /**
     * Sets the number of blocks.
     *
     * @param blocks the number of blocks to set
     * @return the updated ContentLimitBuilder object
     */
    public ContentLimitBuilder blocks(int blocks) {
        this.blocks = blocks;
        return this;
    }

    /**
     * Sets the number of non-air blocks.
     *
     * @param nonAirBlocks the number of non-air blocks
     * @return the ContentLimitBuilder object for method chaining
     */
    public ContentLimitBuilder nonAirBlocks(int nonAirBlocks) {
        this.nonAirBlocks = nonAirBlocks;
        return this;
    }

    /**
     * Sets the number of creatures.
     *
     * @param creatures the number of creatures to be set
     * @return the ContentLimitBuilder instance
     */
    public ContentLimitBuilder creatures(int creatures) {
        this.creatures = creatures;
        return this;
    }

    /**
     * Sets the number of non-creatures.
     *
     * @param nonCreatures the number of non-creatures to be set in the content limit
     * @return the ContentLimitBuilder object for method chaining
     */
    public ContentLimitBuilder nonCreatures(int nonCreatures) {
        this.nonCreatures = nonCreatures;
        return this;
    }

    public ContentLimit build() {
        return new ContentLimit(blocks, nonAirBlocks, creatures, nonCreatures);
    }
}
