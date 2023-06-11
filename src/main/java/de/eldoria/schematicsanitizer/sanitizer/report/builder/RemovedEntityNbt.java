package de.eldoria.schematicsanitizer.sanitizer.report.builder;

import com.sk89q.worldedit.entity.BaseEntity;
import com.sk89q.worldedit.util.Location;
import org.jetbrains.annotations.Nullable;

public record RemovedEntityNbt(Location location, BaseEntity type, NbtRemovalCause removalCause, String tag, @Nullable String value) {
}
