package de.eldoria.schematicsanitizer.sanitizer.settings;

import com.sk89q.worldedit.world.entity.EntityTypes;
import de.eldoria.schematicsanitizer.sanitizer.filter.Filter;
import de.eldoria.schematicsanitizer.sanitizer.limit.Limit;
import de.eldoria.schematicsanitizer.sanitizer.settings.builder.SettingsBuilder;
import org.bukkit.Material;

public record Settings(Filter filter, Limit limit) {
    public static final Settings DEFAULT = new SettingsBuilder()
            .filter(filter -> filter
                    .blockFilter(blocks -> blocks
                            .withMaterialBlacklist(
                                    Material.COMMAND_BLOCK,
                                    Material.REPEATING_COMMAND_BLOCK,
                                    Material.CHAIN_COMMAND_BLOCK,
                                    Material.STRUCTURE_BLOCK)
                    )
                    .entityFilter(entities -> entities
                            .removeCreature(true)
                            .removeNonCreatures(false)
                            .withEntityBlacklist(
                                    EntityTypes.COMMAND_BLOCK_MINECART,
                                    EntityTypes.FALLING_BLOCK,
                                    EntityTypes.POTION)
                    )
                    .withTextBlacklist(
                            "clickEvent",
                            "run_command"
                    )
                    .withNbtBlacklist(
                            "LootTable",
                            "ArmorItem",
                            "ArmorItems",
                            "HandItem",
                            "HandItems",
                            "FireworksItem",
                            "Item",
                            "Items",
                            "DecorItem",
                            "Inventory",
                            "buy",
                            "buyB",
                            "sell",
                            "SaddleItem"
                    )
            )
            .limit(limit -> limit
                    .size(600)
                    .contentLimit(content -> content
                            .blocks(50000)
                            .creatures(50)
                            .nonCreatures(600)
                    )
            )
            .build();
}
