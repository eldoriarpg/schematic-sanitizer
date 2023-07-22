/*
 *     SPDX-License-Identifier: LGPL-3.0-or-later
 *
 *     Copyright (C) RainbowDashLabs and Contributor
 */

package de.eldoria.schematicsanitizer.rendering;

import de.eldoria.schematicsanitizer.rendering.subreports.LimitRenderer;
import de.eldoria.schematicsanitizer.rendering.subreports.sized.BlockNbtRenderer;
import de.eldoria.schematicsanitizer.rendering.subreports.sized.BlockRenderer;
import de.eldoria.schematicsanitizer.rendering.subreports.sized.EntityNbtRenderer;
import de.eldoria.schematicsanitizer.rendering.subreports.sized.EntityRenderer;
import de.eldoria.schematicsanitizer.rendering.subreports.sized.SizedReportRenderer;
import de.eldoria.schematicsanitizer.sanitizer.report.SanitizerReport;
import de.eldoria.schematicsanitizer.sanitizer.report.subreports.sized.SizedReport;
import de.eldoria.schematicsanitizer.sanitizer.settings.Settings;
import org.jetbrains.annotations.Nullable;

import java.nio.file.Path;
import java.util.List;
import java.util.function.Function;

import static de.eldoria.schematicsanitizer.util.Text.numbers;

public record ReportRenderer(SanitizerReport report, Settings settings, EntityRenderer entities, BlockRenderer blocks,
                             EntityNbtRenderer entitiesNbt, BlockNbtRenderer blocksNbt, LimitRenderer limits) {

    public static ReportRenderer create(SanitizerReport report, Settings settings) {
        return new ReportRenderer(report, settings,
                EntityRenderer.create(report.entities()),
                BlockRenderer.create(report.blocks()),
                EntityNbtRenderer.create(report.entitiesNbt()),
                BlockNbtRenderer.create(report.blocksNbt()),
                LimitRenderer.create(report.limits()));
    }

    /**
     * Generates a component for the schematic report based on the provided settings.
     *
     * @return The generated component as a string.
     */
    public String component() {
        return """
                <header>Schematic Report</header><default> <hover:show_text:'<default>%s'>File</hover>
                %s
                <section>Removed Content:<default>
                %s
                """.stripIndent()
                .formatted(path(), limits().summary(settings), summary().indent(2));
    }

    public String shortComponent(String regPath) {
        List<String> errors = limits.errors(settings);
        if (!blocks().isEmpty()) {
            errors.add("%s<default> illegal blocks found".formatted(numbers(blocks())));
        }
        if (!blocksNbt().isEmpty()) {
            errors.add("%s<default> illegal NBT entries on blocks found".formatted(numbers(blocksNbt())));
        }
        if (!entities().isEmpty()) {
            errors.add("%s<default> illegal entities found".formatted(numbers(entities())));
        }
        if (!entitiesNbt().isEmpty()) {
            errors.add("%s<default> illegal NBT entries on entities found".formatted(numbers(entitiesNbt())));
        }
        if (!blocks().isEmpty()) {
            int perc = Math.round(limits().content().nonAirBlocks() / (float) blocks().size() * 100);
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
    public String pageComponent(Function<ReportRenderer, SizedReportRenderer<?>> map, int page, int size) {
        return map.apply(this).pageComponent(page, size);
    }

    private String summary() {
        return """
                %s<default> illegal blocks found %s
                %s<default> illegal NBT entries on blocks found %s
                %s<default> illegal entities found %s
                %s<default> illegal NBT entries on entities found %s"""
                .formatted(numbers(blocks()), blocks().isEmpty() ? "" : showButton("blocks"),
                        numbers(blocksNbt()), blocksNbt().isEmpty() ? "" : showButton("blocks_nbt"),
                        numbers(entities()), entities().isEmpty() ? "" : showButton("entities"),
                        numbers(entitiesNbt()), entitiesNbt().isEmpty() ? "" : showButton("entities_nbt"));
    }

    private String showButton(String subCommand) {
        return "<click:run_command:/schematicsanitizer report page %s 0><interact><hover:show_text:'<default>Click me'>[Show]</hover><default></click>".formatted(subCommand);
    }

    public @Nullable Path newPath() {
        return report.newPath();
    }

    public Path path() {
        return report.path();
    }
}
