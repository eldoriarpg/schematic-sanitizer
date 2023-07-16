package de.eldoria.schematicsanitizer.sanitizer.filter.builder;

import com.sk89q.worldedit.world.entity.EntityType;
import de.eldoria.schematicsanitizer.sanitizer.filter.EntityFilter;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * A class for building an EntityFilter object.
 */

public class EntityFilterBuilder {
    private final Set<EntityType> entityBlacklist = new HashSet<>();
    private boolean removeCreature = false;
    private boolean removeNonCreatures = false;

    /**
     * Set whether to remove creatures from the EntityFilterBuilder.
     *
     * @param removeCreature a boolean indicating whether to remove creatures
     * @return the updated EntityFilterBuilder instance
     */
    public EntityFilterBuilder removeCreature(boolean removeCreature) {
        this.removeCreature = removeCreature;
        return this;
    }

    /**
     * Sets the flag to remove non-creature entities in the EntityFilterBuilder.
     *
     * @param removeNonCreatures true to remove non-creature entities, false otherwise
     * @return the current EntityFilterBuilder instance
     */
    public EntityFilterBuilder removeNonCreatures(boolean removeNonCreatures) {
        this.removeNonCreatures = removeNonCreatures;
        return this;
    }

    /**
     * Sets the blacklist of entity types for filtering.
     *
     * @param entities the entity types to be blacklisted
     * @return the EntityFilterBuilder instance with the updated entity blacklist
     */
    public EntityFilterBuilder withEntityBlacklist(EntityType... entities) {
        return withEntityBlacklist(Arrays.stream(entities).filter(Objects::nonNull).toList());
    }

    /**
     * Adds the given collection of entity types to the entity blacklist of the EntityFilterBuilder.
     *
     * @param text the collection of entity types to be added to the blacklist
     * @return the EntityFilterBuilder with the updated entity blacklist
     */
    public EntityFilterBuilder withEntityBlacklist(Collection<EntityType> text) {
        entityBlacklist.addAll(text);
        return this;
    }

    /**
     * Builds an instance of EntityFilter with configured parameters.
     *
     * @return A new instance of EntityFilter with the configured parameters.
     */
    public EntityFilter build() {
        return new EntityFilter(removeCreature, removeNonCreatures, entityBlacklist);
    }
}
