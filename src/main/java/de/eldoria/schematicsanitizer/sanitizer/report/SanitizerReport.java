package de.eldoria.schematicsanitizer.sanitizer.report;

import de.eldoria.schematicsanitizer.sanitizer.settings.Settings;

import java.util.function.Function;

public record SanitizerReport(EntityReport entities, BlockReport blocks, EntityNbtReport entitiesNbt,
                              BlockNbtReport blocksNbt, LimitReport limits) {
    public String component(Settings settings) {
        return """
                <bold><gold>Schematic Report</bold><default>
                %s
                <gold>Removed Content:<default>
                %s
                """.stripIndent()
                .formatted(limits.summary(settings),summary().indent(2));
    }

    private String summary() {
        return """
                %d illegal blocks found %s
                %d illegal NBT entries on blocks found %s
                %d illegal entities found %s
                %d illegal NBT entries on entities found %s
                """.formatted(blocks.size(), blocks.isEmpty() ? "" : showButton("blocks"),
                        blocksNbt.size(), blocksNbt.isEmpty() ? "" : showButton("blocks_nbt"),
                        entities.size(), entities.isEmpty() ? "" : showButton("entities"),
                        entitiesNbt.size(), entitiesNbt.isEmpty() ? "" : showButton("entities_nbt"));
    }

    private String showButton(String subCommand) {
        return "<click:run_command:/schematiccleaner report page %s 0><yellow><hover:show_text:'Click me'>[Show]</hover><default></click>".formatted(subCommand);
    }

    public String pageComponent(Function<SanitizerReport, SizedReport<?>> map, int page, int size) {
        return map.apply(this).pageComponent(page, size);
    }
}
