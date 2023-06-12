package de.eldoria.schematicsanitizer.configuration.serialization.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.sk89q.worldedit.world.entity.EntityType;

import java.io.IOException;

public class EntityTypeSerializer extends JsonSerializer<EntityType> {
    @Override
    public void serialize(EntityType value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeString(value.getId());
    }
}
