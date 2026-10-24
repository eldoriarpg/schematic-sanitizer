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

public class EntityTypeDeserializer extends ValueDeserializer<EntityType> {
    @Override
    public EntityType deserialize(JsonParser p, DeserializationContext ctxt) {
        return EntityTypes.get(p.getValueAsString());
    }
}
