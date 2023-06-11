package de.eldoria.schematicsanitizer.sanitizer.report.builder;

import com.sk89q.worldedit.entity.BaseEntity;
import com.sk89q.worldedit.util.Location;
import de.eldoria.schematicsanitizer.sanitizer.report.EntityNbtReport;
import de.eldoria.schematicsanitizer.sanitizer.report.cause.NbtRemovalCause;
import de.eldoria.schematicsanitizer.sanitizer.report.entities.RemovedEntityNbt;

public class EntityNbtReportBuilder extends BaseReportBuilder<RemovedEntityNbt> {
    public void removed(Location location, BaseEntity entity, NbtRemovalCause cause, String key, String text) {
        removed(new RemovedEntityNbt(location, entity, cause, key, text));
    }

    public void removed(Location location, BaseEntity entity, NbtRemovalCause cause, String key) {
        removed(location, entity, cause, key, null);
    }

    public EntityNbtReport build() {
        return new EntityNbtReport(removed());
    }
}
