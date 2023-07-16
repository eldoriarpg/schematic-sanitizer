package de.eldoria.schematicsanitizer.sanitizer.report.builder;

import com.sk89q.worldedit.entity.BaseEntity;
import com.sk89q.worldedit.util.Location;
import de.eldoria.schematicsanitizer.sanitizer.report.EntityNbtReport;
import de.eldoria.schematicsanitizer.sanitizer.report.cause.NbtRemovalCause;
import de.eldoria.schematicsanitizer.sanitizer.report.entities.RemovedEntityNbt;

/**
 * This class represents a builder for generating entity NBT reports.
 * It extends the BaseReportBuilder class and is specifically designed for building reports on removed entity NBT data.
 */

public class EntityNbtReportBuilder extends BaseReportBuilder<RemovedEntityNbt> {
    /**
     * Logs removal of a specific NBT key and its associated text from an entity at a certain location.
     *
     * @param location the location where the entity is located
     * @param entity the entity from which the NBT key and text should be removed
     * @param cause the cause of the removal
     * @param key the NBT key to be removed
     * @param text the associated text to be removed
     */
    public void removed(Location location, BaseEntity entity, NbtRemovalCause cause, String key, String text) {
        removed(new RemovedEntityNbt(location, entity, cause, key, text));
    }

    /**
     * Logs removal of a key from the NBT data of a specific entity at a given location.
     *
     * @param location the location at which the entity is present
     * @param entity the entity whose NBT data needs to be modified
     * @param cause the cause of the removal
     * @param key the key to be removed from the NBT data
     */
    public void removed(Location location, BaseEntity entity, NbtRemovalCause cause, String key) {
        removed(location, entity, cause, key, null);
    }

    /**
     * Builds an EntityNbtReport object.
     *
     * @return A new EntityNbtReport object populated with the result of the removed() method.
     */
    public EntityNbtReport build() {
        return new EntityNbtReport(removed());
    }
}
