package de.eldoria.schematicsanitizer.sanitizer.report.cause;

/**
 * The `BlockRemovalCause` class is an abstract class that extends the `Cause` class.
 * It represents the cause of a block removal event.
 */

public abstract class BlockRemovalCause extends Cause {
    /**
     * This variable represents the block removal cause for the blacklist.
     */
    public static final BlockRemovalCause BLACKLIST = new BlockRemovalCause("Blacklist") {
    };

    public BlockRemovalCause(String name) {
        super(name);
    }
}
