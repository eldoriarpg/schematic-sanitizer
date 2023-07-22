/*
 *     SPDX-License-Identifier: LGPL-3.0-or-later
 *
 *     Copyright (C) RainbowDashLabs and Contributor
 */

package de.eldoria.schematicsanitizer.sanitizer.report;

import de.eldoria.schematicsanitizer.sanitizer.Sanitizer;
import de.eldoria.schematicsanitizer.sanitizer.settings.Settings;
import org.jetbrains.annotations.Nullable;

import java.nio.file.Path;
import java.util.List;
import java.util.function.Function;

import static de.eldoria.schematicsanitizer.util.Text.numbers;


/**
 * A report representing actions taken by a {@link Sanitizer} process.
 */
public final class SanitizerReport {
    private final Path path;
    private final @Nullable Path newPath;
    private final EntityReport entities;
    private final BlockReport blocks;
    private final EntityNbtReport entitiesNbt;
    private final BlockNbtReport blocksNbt;
    private final LimitReport limits;

    /**
     * @param newPath     the new path of the file if one was created
     * @param entities    the removed entities because of their type
     * @param blocks      the removed blocks because of their type
     * @param entitiesNbt the removed entities because of their tags
     * @param blocksNbt   the removed blocks because of their tags
     * @param limits      the limits or the schematics
     */
    public SanitizerReport(Path path, @Nullable Path newPath, EntityReport entities, BlockReport blocks,
                           EntityNbtReport entitiesNbt,
                           BlockNbtReport blocksNbt, LimitReport limits) {
        this.path = path;
        this.newPath = newPath;
        this.entities = entities;
        this.blocks = blocks;
        this.entitiesNbt = entitiesNbt;
        this.blocksNbt = blocksNbt;
        this.limits = limits;
    }

    /**
     * Generates a component for the schematic report based on the provided settings.
     *
     * @param settings The settings for generating the component.
     * @return The generated component as a string.
     */
    public String component(Settings settings) {
        return """
                <header>Schematic Report</header><default> <hover:show_text:'<default>%s'>File</hover>
                %s
                <section>Removed Content:<default>
                %s
                """.stripIndent()
                .formatted(path, limits.summary(settings), summary().indent(2));
    }

    public String shortComponent(Settings settings, String regPath) {
        List<String> errors = limits.errors(settings);
        if (!blocks.isEmpty()) {
            errors.add("%s<default> illegal blocks found".formatted(numbers(blocks)));
        }
        if (!blocksNbt.isEmpty()) {
            errors.add("%s<default> illegal NBT entries on blocks found".formatted(numbers(blocksNbt)));
        }
        if (!entities.isEmpty()) {
            errors.add("%s<default> illegal entities found".formatted(numbers(entities)));
        }
        if (!entitiesNbt.isEmpty()) {
            errors.add("%s<default> illegal NBT entries on entities found".formatted(numbers(entitiesNbt)));
        }
        if (!blocks.isEmpty()) {
            int perc = Math.round(limits.content().nonAirBlocks() / (float) blocks.size() * 100);
            if (perc >= 90) {
                errors.add("<bad>%d%% <default>of block were removed".formatted(perc));
            }
        }

        if (!errors.isEmpty())
            errors.add("<click:run_command:'/schematicsanitizer report load %s'><interact>[Full report]</click>".formatted(regPath));
        return errors.isEmpty() ? "<good>No problems found!" : String.join("\n", errors);
    }

    /**
     * Returns a page of a subcomponent, which has to be a {@link SizedReport}
     *
     * @param map  The Function object to be applied.
     * @param page The page number.
     * @param size The size of each page.
     * @return The result of executing the pageComponent method on the
     * Function object applied to the current instance of this class.
     */
    public String pageComponent(Function<SanitizerReport, SizedReport<?>> map, int page, int size) {
        return map.apply(this).pageComponent(page, size);
    }

    @Nullable
    public Path newPath() {
        return newPath;
    }

    public EntityReport entities() {
        return entities;
    }

    public BlockReport blocks() {
        return blocks;
    }

    public EntityNbtReport entitiesNbt() {
        return entitiesNbt;
    }

    public BlockNbtReport blocksNbt() {
        return blocksNbt;
    }

    public LimitReport limits() {
        return limits;
    }

    private String summary() {
        return """
                %s<default> illegal blocks found %s
                %s<default> illegal NBT entries on blocks found %s
                %s<default> illegal entities found %s
                %s<default> illegal NBT entries on entities found %s"""
                .formatted(numbers(blocks), blocks.isEmpty() ? "" : showButton("blocks"),
                        numbers(blocksNbt), blocksNbt.isEmpty() ? "" : showButton("blocks_nbt"),
                        numbers(entities), entities.isEmpty() ? "" : showButton("entities"),
                        numbers(entitiesNbt), entitiesNbt.isEmpty() ? "" : showButton("entities_nbt"));
    }

    private String showButton(String subCommand) {
        return "<click:run_command:/schematicsanitizer report page %s 0><interact><hover:show_text:'<default>Click me'>[Show]</hover><default></click>".formatted(subCommand);
    }

    /**
     * The path of the checked file.
     *
     * @return path
     */
    public Path path() {
        return path;
    }
}
