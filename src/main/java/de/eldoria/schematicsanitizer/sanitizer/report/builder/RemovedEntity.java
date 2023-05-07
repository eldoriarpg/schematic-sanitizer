package de.eldoria.schematicsanitizer.sanitizer.report.builder;

import com.sk89q.worldedit.entity.BaseEntity;
import com.sk89q.worldedit.util.Location;

public record RemovedEntity(Location location, BaseEntity type, EntityRemovalCause removalCause) {
}
