/*
 *     SPDX-License-Identifier: AGPL-3.0-only
 *
 *     Copyright (C) EldoriaRPG Team and Contributor
 */
package de.eldoria.schematicsanitizer.configuration.serialization;

import com.sk89q.worldedit.world.entity.EntityType;
import de.eldoria.schematicsanitizer.configuration.serialization.deserializer.EntityTypeDeserializer;
import de.eldoria.schematicsanitizer.configuration.serialization.serializer.EntityTypeSerializer;
import tools.jackson.databind.module.SimpleModule;

/**
 * Reads and writes WorldEdit entity types by their id, like {@code minecraft:falling_block}.
 */
public class CustomModule extends SimpleModule {
    public CustomModule() {
        super("SchematicSanitizer");
        addSerializer(EntityType.class, new EntityTypeSerializer());
        addDeserializer(EntityType.class, new EntityTypeDeserializer());
    }
}
