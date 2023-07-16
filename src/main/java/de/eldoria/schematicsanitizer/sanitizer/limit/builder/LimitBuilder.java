package de.eldoria.schematicsanitizer.sanitizer.limit.builder;

import de.eldoria.schematicsanitizer.sanitizer.limit.Limit;

import java.util.function.Consumer;

/**
 * A builder class for creating instances of {@link Limit}.
 * This class provides methods to set the size and the content limit of the limit.
 */

public class LimitBuilder {
    private final ContentLimitBuilder contentLimit = new ContentLimitBuilder();
    private int size = 600;

    /**
     * Sets the size of the LimitBuilder.
     *
     * @param size the size to set
     * @return the LimitBuilder instance with the specified size
     */
    public LimitBuilder size(int size) {
        this.size = size;
        return this;
    }

    /**
     * Sets the content limit by accepting a consumer of type {@link ContentLimitBuilder}.
     * The content limit is used to define the maximum content that can be consumed or processed.
     *
     * @param limit The consumer function to set the content limit using the {@link ContentLimitBuilder}.
     * @return This LimitBuilder instance.
     */
    public LimitBuilder contentLimit(Consumer<ContentLimitBuilder> limit) {
        limit.accept(contentLimit);
        return this;
    }

    /**
     * Constructs a {@link Limit} object based on the specified size and content limit.
     *
     * @return A {@link Limit} object with the specified size and content limit.
     */
    public Limit build() {
        return new Limit(size, contentLimit.build());
    }
}
