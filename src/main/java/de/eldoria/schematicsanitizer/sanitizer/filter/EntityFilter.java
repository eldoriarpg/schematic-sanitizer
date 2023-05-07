package de.eldoria.schematicsanitizer.sanitizer.filter;

import com.sk89q.worldedit.world.entity.EntityType;

import java.util.Set;

public record EntityFilter(boolean removeCreature, boolean removeNonCreatures, Set<EntityType> entityBlacklist) {
    public boolean isBlacklisted(EntityType entityType) {
        return entityBlacklist.contains(entityType);
    }
}
