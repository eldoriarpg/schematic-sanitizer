package de.eldoria.schematicsanitizer.sanitizer.report.cause;

public abstract class EntityRemovalCause extends Cause {
    public static final EntityRemovalCause BLACKLIST = new EntityRemovalCause("Blacklist") {
    };
    public static final EntityRemovalCause NON_CREATURE = new EntityRemovalCause("Non Creature") {
    };
    public static final EntityRemovalCause CREATURE = new EntityRemovalCause("Creature") {
    };
    public static final EntityRemovalCause UNKNOWN_TYPE = new EntityRemovalCause("Unknown type") {
    };

    public EntityRemovalCause(String name) {
        super(name);
    }
}
