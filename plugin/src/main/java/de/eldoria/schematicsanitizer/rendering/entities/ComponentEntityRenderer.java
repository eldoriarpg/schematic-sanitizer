/*
 *     SPDX-License-Identifier: LGPL-3.0-or-later
 *
 *     Copyright (C) RainbowDashLabs and Contributor
 */

package de.eldoria.schematicsanitizer.rendering.entities;

import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldedit.registry.Keyed;
import com.sk89q.worldedit.util.Location;
import de.eldoria.schematicsanitizer.sanitizer.report.cause.Cause;
import net.kyori.adventure.translation.Translatable;

/**
 * The ComponentEntity interface defines methods for interacting with a component entity.
 * <p>
 * This interface provides methods for retrieving component, coordinates, name, cause, and meta of an entity.
 */

public interface ComponentEntityRenderer {
    /**
     * Retrieves the component of the object.
     *
     * @return The component of the object as a string.
     */
    String component();

    /**
     * Converts the given BlockVector3 into a string representation of its coordinates.
     *
     * @param vec the BlockVector3 to convert
     * @return the string representation of the coordinates in the format {@code <#8f8c8c>%d %d %d}, where %d represents
     * the block X, Y, and Z coordinates respectively
     */
    default String coords(BlockVector3 vec) {
        return "<#8f8c8c>%d %d %d".formatted(vec.getBlockX(), vec.getBlockY(), vec.getBlockZ());
    }

    /**
     * Retrieves the coordinates as a String representation of the given Location object.
     *
     * @param vec the Location object to retrieve the coordinates from
     * @return a String representation of the coordinates
     */
    default String coords(Location vec) {
        return coords(vec.toBlockPoint());
    }

    /**
     * Returns a string formatted with a given Translatable object.
     *
     * @param translatable the Translatable object to be formatted
     * @return the formatted string
     */
    default String name(Translatable translatable, Keyed keyed) {
        return "<hover:show_text:'<default>%s'><#ffdd00><lang:%s></hover>".formatted(keyed.getId(), translatable.translationKey());
    }

    /**
     * Returns the cause object formatted as a string in a specific style.
     *
     * @param object the Cause object to be converted to a string
     * @return the formatted string representation of the Cause object
     */
    default String cause(Cause object) {
        return "<#f58f2a>" + object;
    }

    /**
     * Adds a prefix to the string representation of an object and returns the modified string.
     *
     * @param object The object whose string representation is to be modified.
     * @return The modified string with a prefix.
     */
    default String meta(Object object) {
        return "<#0079b5>" + object;
    }
}
