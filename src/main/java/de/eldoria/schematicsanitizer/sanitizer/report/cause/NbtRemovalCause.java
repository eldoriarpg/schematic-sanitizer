package de.eldoria.schematicsanitizer.sanitizer.report.cause;

public abstract class NbtRemovalCause extends Cause{
    public static final NbtRemovalCause TEXT_BLACKLIST = new NbtRemovalCause("Text blacklist") {
    };
    public static final NbtRemovalCause ILLEGAL_TAG = new NbtRemovalCause("Illegal Tag") {
    };

    public NbtRemovalCause(String name) {
        super(name);
    }
}
