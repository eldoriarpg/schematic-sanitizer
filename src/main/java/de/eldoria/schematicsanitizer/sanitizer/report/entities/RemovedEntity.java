package de.eldoria.schematicsanitizer.sanitizer.report.entities;

import com.sk89q.worldedit.entity.BaseEntity;
import com.sk89q.worldedit.util.Location;
import de.eldoria.schematicsanitizer.sanitizer.report.cause.EntityRemovalCause;

public record RemovedEntity(Location location, BaseEntity type, EntityRemovalCause removalCause) {
}
