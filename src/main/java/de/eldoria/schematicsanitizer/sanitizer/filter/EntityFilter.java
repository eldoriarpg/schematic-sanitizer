package de.eldoria.schematicsanitizer.sanitizer.filter;

import com.sk89q.worldedit.world.entity.EntityType;

import java.util.Set;

public record EntityFilter(boolean removeCreature, boolean removeNonCreatures, Set<EntityType> entityBlacklist) {
    /**
     * Checks if the given entity type is blacklisted.
     *
     * @param entityType the entity type to be checked
     * @return true if the entity type is blacklisted, false otherwise
     */
    public boolean isBlacklisted(EntityType entityType) {
        return entityBlacklist.contains(entityType);
    }
}
