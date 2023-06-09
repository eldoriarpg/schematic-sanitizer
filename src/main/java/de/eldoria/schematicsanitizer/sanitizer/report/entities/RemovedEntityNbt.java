package de.eldoria.schematicsanitizer.sanitizer.report.entities;

import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.entity.BaseEntity;
import com.sk89q.worldedit.util.Location;
import de.eldoria.schematicsanitizer.sanitizer.report.cause.NbtRemovalCause;
import org.jetbrains.annotations.Nullable;

public record RemovedEntityNbt(Location location, BaseEntity type, NbtRemovalCause removalCause, String tag,
                               @Nullable String value) implements ComponentEntity {
    @Override
    public String component() {
        if (value == null) {
            return "%s %s %s: %s".formatted(coords(location), name(BukkitAdapter.adapt(type.getType())), cause(removalCause), meta(tag));
        }
        return "%s %s %s: %s".formatted(coords(location), name(BukkitAdapter.adapt(type.getType())), cause(removalCause), meta("{%s:%s}".formatted(tag, value)));
    }

}
