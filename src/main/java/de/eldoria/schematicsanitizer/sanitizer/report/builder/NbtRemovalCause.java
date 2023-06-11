package de.eldoria.schematicsanitizer.sanitizer.report.builder;

public interface NbtRemovalCause {
    NbtRemovalCause TEXT_BLACKLIST = new NbtRemovalCause() {
    };
    NbtRemovalCause ILLEGAL_TAG = new NbtRemovalCause() {
    };
}
