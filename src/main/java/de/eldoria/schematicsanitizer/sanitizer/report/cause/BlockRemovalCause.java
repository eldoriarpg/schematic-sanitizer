package de.eldoria.schematicsanitizer.sanitizer.report.cause;

public abstract class BlockRemovalCause extends Cause {
    public static final BlockRemovalCause BLACKLIST = new BlockRemovalCause("Blacklist") {
    };

    public BlockRemovalCause(String name) {
        super(name);
    }
}
