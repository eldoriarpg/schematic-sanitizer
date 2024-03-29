/*
 *     SPDX-License-Identifier: LGPL-3.0-or-later
 *
 *     Copyright (C) RainbowDashLabs and Contributor
 */

package de.eldoria.schematicsanitizer.configuration.serialization.deserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.sk89q.worldedit.world.entity.EntityType;
import com.sk89q.worldedit.world.entity.EntityTypes;

import java.io.IOException;

public class EntityTypeDeserializer extends JsonDeserializer<EntityType> {
    @Override
    public EntityType deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        return EntityTypes.get(ctxt.readValue(p, String.class));
    }
}
