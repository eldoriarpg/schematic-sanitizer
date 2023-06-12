package de.eldoria.schematicsanitizer.sanitizer.limit.builder;

import de.eldoria.schematicsanitizer.sanitizer.limit.ContentLimit;

public class ContentLimitBuilder {
    private int blocks = 50000;
    private int nonAirBlocks = 50000;
    private int creatures = 50;
    private int nonCreatures = 600;

    public ContentLimitBuilder blocks(int blocks) {
        this.blocks = blocks;
        return this;
    }

    public ContentLimitBuilder nonAirBlocks(int nonAirBlocks) {
        this.nonAirBlocks = nonAirBlocks;
        return this;
    }

    public ContentLimitBuilder creatures(int creatures) {
        this.creatures = creatures;
        return this;
    }

    public ContentLimitBuilder nonCreatures(int nonCreatures) {
        this.nonCreatures = nonCreatures;
        return this;
    }

    public ContentLimit build() {
        return new ContentLimit(blocks, nonAirBlocks, creatures, nonCreatures);
    }
}
