package de.eldoria.schematicsanitizer.sanitizer.report.builder;

public interface EntityRemovalCause {
    EntityRemovalCause BLACKLIST = new EntityRemovalCause() {
    };
    EntityRemovalCause NON_CREATURE = new EntityRemovalCause() {
    };
    EntityRemovalCause CREATURE = new EntityRemovalCause() {
    };
    EntityRemovalCause UNKNOWN_TYPE = new EntityRemovalCause() {
    };
}
