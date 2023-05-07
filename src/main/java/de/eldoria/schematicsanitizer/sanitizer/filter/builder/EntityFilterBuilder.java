package de.eldoria.schematicsanitizer.sanitizer.filter.builder;

import com.sk89q.worldedit.world.entity.EntityType;
import de.eldoria.schematicsanitizer.sanitizer.filter.EntityFilter;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class EntityFilterBuilder {
    private boolean removeCreature = false;
    private boolean removeNonCreatures = false;
    private final Set<EntityType> entityBlacklist = new HashSet<>();

    public EntityFilterBuilder removeCreature(boolean removeCreature) {
        this.removeCreature = removeCreature;
        return this;
    }

    public EntityFilterBuilder removeNonCreatures(boolean removeNonCreatures) {
        this.removeNonCreatures = removeNonCreatures;
        return this;
    }

    public EntityFilterBuilder withEntityBlacklist(EntityType... text) {
        return withEntityBlacklist(List.of(text));
    }

    public EntityFilterBuilder withEntityBlacklist(Collection<EntityType> text) {
        entityBlacklist.addAll(text);
        return this;
    }

        public EntityFilter build(){
        return new EntityFilter(removeCreature, removeNonCreatures, entityBlacklist);
    }
}
