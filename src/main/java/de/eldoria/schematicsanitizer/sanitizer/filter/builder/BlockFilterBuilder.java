package de.eldoria.schematicsanitizer.sanitizer.filter.builder;

import de.eldoria.schematicsanitizer.sanitizer.filter.BlockFilter;
import org.bukkit.Material;

import java.util.Arrays;
import java.util.Collection;
import java.util.EnumSet;
import java.util.Objects;
import java.util.Set;

/**
 * A builder class for creating BlockFilter objects.
 */

public class BlockFilterBuilder {
    private final Set<Material> materialBlacklist = EnumSet.noneOf(Material.class);

    /**
     * Sets the blacklist of materials for the block filter.
     *
     * @param materials The materials to be added to the blacklist. Non-null materials will be added to the list.
     * @return The BlockFilterBuilder instance with the updated material blacklist.
     */
    public BlockFilterBuilder withMaterialBlacklist(Material... materials) {
        return withMaterialBlacklist(Arrays.stream(materials).filter(Objects::nonNull).toList());
    }

    /**
     * Adds the given collection of materials to the material blacklist.
     *
     * @param text The collection of materials to be added to the material blacklist.
     * @return The instance of BlockFilterBuilder after adding the materials to the material blacklist.
     */
    public BlockFilterBuilder withMaterialBlacklist(Collection<Material> text) {
        materialBlacklist.addAll(text);
        return this;
    }

    /**
     * Builds a BlockFilter object with the specified material blacklist.
     *
     * @return A BlockFilter object with the specified material blacklist
     */
    public BlockFilter build(){
        return new BlockFilter(materialBlacklist);
    }
}
