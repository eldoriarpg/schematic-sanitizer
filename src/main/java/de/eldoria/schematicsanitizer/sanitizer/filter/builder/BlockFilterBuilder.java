package de.eldoria.schematicsanitizer.sanitizer.filter.builder;

import de.eldoria.schematicsanitizer.sanitizer.filter.BlockFilter;
import org.bukkit.Material;

import java.util.Collection;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;

public class BlockFilterBuilder {
    private final Set<Material> materialBlacklist = EnumSet.noneOf(Material.class);

    public BlockFilterBuilder withMaterialBlacklist(Material... text) {
        return withMaterialBlacklist(List.of(text));
    }

    public BlockFilterBuilder withMaterialBlacklist(Collection<Material> text) {
        materialBlacklist.addAll(text);
        return this;
    }

    public BlockFilter build(){
        return new BlockFilter(materialBlacklist);
    }
}
