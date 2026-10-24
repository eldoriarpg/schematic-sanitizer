/*
 *     SPDX-License-Identifier: AGPL-3.0-only
 *
 *     Copyright (C) EldoriaRPG Team and Contributor
 */
package de.eldoria.schematicsanitizer.configuration.serialization.serializer;

import com.sk89q.worldedit.world.entity.EntityType;
import tools.jackson.core.JsonGenerator;
import tools.jackson.databind.SerializationContext;
import tools.jackson.databind.ValueSerializer;

public class EntityTypeSerializer extends ValueSerializer<EntityType> {
    @Override
    public void serialize(EntityType value, JsonGenerator gen, SerializationContext ctxt) {
        gen.writeString(value.getId());
    }
}
