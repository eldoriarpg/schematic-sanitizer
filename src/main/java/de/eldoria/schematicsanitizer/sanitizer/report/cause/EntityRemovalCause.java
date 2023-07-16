package de.eldoria.schematicsanitizer.sanitizer.report.cause;

/**
 * The EntityRemovalCause class represents the cause for the removal of an entity.
 *
 * This class extends the Cause class and provides several predefined instances
 * representing common removal causes, such as being in a blacklist, not being a creature,
 * being a creature, or having an unknown type.
 */

public abstract class EntityRemovalCause extends Cause {
    /**
     * Represents a cause for entity removal related to a blacklist.
     */
    public static final EntityRemovalCause BLACKLIST = new EntityRemovalCause("Blacklist") {
    };
    /**
     * Represents the reason for removing a non-creature entity.
     */
    public static final EntityRemovalCause NON_CREATURE = new EntityRemovalCause("Non Creature") {
    };
    /**
     * Represents a constant variable that defines the entity removal cause as a creature.
     */
    public static final EntityRemovalCause CREATURE = new EntityRemovalCause("Creature") {
    };
    /**
     * Represents an unknown type of entity removal cause.
     */
    public static final EntityRemovalCause UNKNOWN_TYPE = new EntityRemovalCause("Unknown type") {
    };

    /**
     * Creates a new instance of EntityRemovalCause with the specified name.
     *
     * @param name the name associated with the cause of entity removal
     */
    public EntityRemovalCause(String name) {
        super(name);
    }
}
