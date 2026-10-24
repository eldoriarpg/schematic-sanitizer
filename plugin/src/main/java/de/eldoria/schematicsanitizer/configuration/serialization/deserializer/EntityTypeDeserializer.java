/*
 *     SPDX-License-Identifier: AGPL-3.0-only
 *
 *     Copyright (C) EldoriaRPG Team and Contributor
 */
package de.eldoria.schematicsanitizer.configuration.serialization.deserializer;

import com.sk89q.worldedit.world.entity.EntityType;
import com.sk89q.worldedit.world.entity.EntityTypes;
import tools.jackson.core.JsonParser;
import tools.jackson.databind.DeserializationContext;
import tools.jackson.databind.ValueDeserializer;

/**
 * Reads entity types by id. An id the server does not know is kept as it is, so it stays in the file and matches no
 * entity, instead of turning into an empty entry.
 */
public class EntityTypeDeserializer extends ValueDeserializer<EntityType> {
    @Override
    public EntityType deserialize(JsonParser p, DeserializationContext ctxt) {
        String id = p.getValueAsString();
        EntityType type = EntityTypes.get(id);
        return type != null ? type : new EntityType(id);
    }
}
