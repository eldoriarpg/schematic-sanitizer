package de.eldoria.schematicsanitizer.sanitizer.report.builder;

import com.sk89q.worldedit.entity.BaseEntity;
import com.sk89q.worldedit.util.Location;

import java.util.ArrayList;
import java.util.List;

public class EntityNbtReportBuilder {
    private final List<RemovedEntityNbt> removed = new ArrayList<>();


    public void removed(RemovedEntityNbt removed) {
        this.removed.add(removed);
    }

    public void removed(Location location, BaseEntity entity, NbtRemovalCause cause, String key, String text) {
        removed(new RemovedEntityNbt(location, entity, cause, key, text));
    }

    public void removed(Location location, BaseEntity entity, NbtRemovalCause cause, String key) {
        removed(location, entity, cause, key, null);
    }
}
