package de.eldoria.schematicsanitizer.sanitizer.report.entities;

import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldedit.util.Location;
import de.eldoria.schematicsanitizer.sanitizer.report.cause.Cause;
import net.kyori.adventure.translation.Translatable;

public interface ComponentEntity {
    String component();

    default String coords(BlockVector3 vec) {
        return "<#8f8c8c>%d %d %d".formatted(vec.getBlockX(), vec.getBlockY(), vec.getBlockZ());
    }

    default String coords(Location vec) {
        return coords(vec.toBlockPoint());
    }

    default String name(Translatable translatable) {
        return "<#ffdd00><lang:%s>".formatted(translatable.translationKey());
    }

    default String cause(Cause object) {
        return "<#f58f2a>" + object;
    }

    default String meta(Object object) {
        return "<#0079b5>" + object;
    }
}
