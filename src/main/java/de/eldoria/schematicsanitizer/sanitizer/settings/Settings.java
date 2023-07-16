package de.eldoria.schematicsanitizer.sanitizer.settings;

import com.sk89q.worldedit.world.entity.EntityTypes;
import de.eldoria.schematicsanitizer.sanitizer.filter.Filter;
import de.eldoria.schematicsanitizer.sanitizer.limit.Limit;
import de.eldoria.schematicsanitizer.sanitizer.settings.builder.SettingsBuilder;
import org.bukkit.Material;

public record Settings(Filter filter, Limit limit) {
    /**
     * Default settings for a certain feature.
     * <p>
     * This constant represents the default settings for the feature. It is a static final variable of type Settings.
     * The settings are constructed using a SettingsBuilder, which allows for configuring various filters and limits.
     * Here is a breakdown of the default settings:
     * <p>
     * Filters:
     * - Block Filter: Allows blocking certain block types based on their materials. The following materials are blacklisted:
     * - COMMAND_BLOCK
     * - REPEATING_COMMAND_BLOCK
     * - CHAIN_COMMAND_BLOCK
     * - STRUCTURE_BLOCK
     * - Entity Filter: Allows filtering entities based on their types. The following entities are blacklisted:
     * - COMMAND_BLOCK_MINECART
     * - FALLING_BLOCK
     * - POTION
     * - Text Blacklist: Contains a list of blacklisted text entries. The following entries are blacklisted:
     * - "clickEvent"
     * - "run_command"
     * - NBT Blacklist: Contains a list of blacklisted NBT tags. The following tags are blacklisted:
     * - "LootTable"
     * - "ArmorItem"
     * - "ArmorItems"
     * - "HandItem"
     * - "HandItems"
     * - "FireworksItem"
     * - "Item"
     * - "Items"
     * - "DecorItem"
     * - "Inventory"
     * - "buy"
     * - "buyB"
     * - "sell"
     * - "SaddleItem"
     * <p>
     * Limits:
     * - Size Limit: Specifies the maximum size of the feature. The size is set to 600.
     * - Content Limit: Specifies the maximum limits for different types of content within the feature:
     * - Blocks: Limited to 50000.
     * - Creatures: Limited to 50.
     * - Non-Creatures: Limited to 600.
     *
     * @see Settings
     * @see SettingsBuilder
     */
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

    public static SettingsBuilder builder() {
        return new SettingsBuilder();
    }
}
