package de.eldoria.schematicsanitizer.sanitizer.report;

public record Report(EntityReport entities, BlockReport blocks, EntityNbtReport entitiesNbt, BlockNbtReport blocksNbt) {
}
