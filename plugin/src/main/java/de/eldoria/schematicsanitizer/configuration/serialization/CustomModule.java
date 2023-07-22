/*
 *     SPDX-License-Identifier: LGPL-3.0-or-later
 *
 *     Copyright (C) RainbowDashLabs and Contributor
 */

package de.eldoria.schematicsanitizer.configuration.serialization;

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.module.SimpleDeserializers;
import com.fasterxml.jackson.databind.module.SimpleSerializers;
import com.sk89q.worldedit.world.entity.EntityType;
import de.eldoria.schematicsanitizer.configuration.serialization.deserializer.EntityTypeDeserializer;
import de.eldoria.schematicsanitizer.configuration.serialization.serializer.EntityTypeSerializer;

public class CustomModule extends Module {
    @Override
    public String getModuleName() {
        return "SchematicSanitizer";
    }

    @Override
    public Version version() {
        return new Version(1, 0, 0, "", "de.eldoria", "schematic-sanitizer");
    }

    @Override
    public void setupModule(SetupContext context) {
        SimpleSerializers serializers = new SimpleSerializers();
        serializers.addSerializer(EntityType.class, new EntityTypeSerializer());
        context.addSerializers(serializers);

        SimpleDeserializers deserializers = new SimpleDeserializers();
        deserializers.addDeserializer(EntityType.class, new EntityTypeDeserializer());
        context.addDeserializers(deserializers);
    }
}
