/*
 *     SPDX-License-Identifier: LGPL-3.0-or-later
 *
 *     Copyright (C) RainbowDashLabs and Contributor
 */

package de.eldoria.schematicsanitizer.sanitizer;

import org.enginehub.linbus.tree.LinCompoundTag;
import org.enginehub.linbus.tree.LinListTag;
import org.enginehub.linbus.tree.LinStringTag;
import org.enginehub.linbus.tree.LinTagType;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class NbtCleanerTest {
    private final List<String> removedTags = new ArrayList<>();
    private final List<String> removedTexts = new ArrayList<>();
    private final NbtCleaner cleaner = new NbtCleaner(Set.of("Items", "LootTable"), Set.of("clickEvent", "run_command"),
            removedTags::add, (key, value) -> removedTexts.add(key));

    private static LinCompoundTag dropper() {
        LinCompoundTag item = LinCompoundTag.builder()
                .putByte("Slot", (byte) 0)
                .putString("id", "minecraft:redstone")
                .putInt("count", 9)
                .build();
        return LinCompoundTag.builder()
                .put("components", LinCompoundTag.builder().build())
                .put("Items", LinListTag.of(LinTagType.compoundTag(), List.of(item)))
                .putString("id", "minecraft:dropper")
                .putInt("x", 0)
                .putInt("y", 9)
                .putInt("z", 0)
                .build();
    }

    @Test
    void removesBlacklistedKeyFromImmutableTag() {
        LinCompoundTag cleaned = cleaner.clean(dropper());

        assertFalse(cleaned.value().containsKey("Items"));
        assertEquals(List.of("Items"), removedTags);
    }

    @Test
    void keepsSiblingsOfNestedCompounds() {
        LinCompoundTag cleaned = cleaner.clean(dropper());

        assertEquals(Set.of("components", "id", "x", "y", "z"), cleaned.value().keySet());
        assertEquals("minecraft:dropper", cleaned.getTag("id", LinTagType.stringTag()).value());
    }

    @Test
    void removesBlacklistedKeyInsideNestedCompound() {
        LinCompoundTag nbt = LinCompoundTag.builder()
                .put("components", LinCompoundTag.builder().putString("LootTable", "chests/x").putInt("keep", 1).build())
                .build();

        LinCompoundTag components = cleaner.clean(nbt).getTag("components", LinTagType.compoundTag());

        assertEquals(Set.of("keep"), components.value().keySet());
    }

    @Test
    void removesBlacklistedTextFromStringList() {
        LinListTag<LinStringTag> messages = LinListTag.of(LinTagType.stringTag(), List.of(
                LinStringTag.of("{\"text\":\"hi\",\"clickEvent\":{\"action\":\"run_command\"}}"),
                LinStringTag.of("{\"text\":\"plain\"}")));
        LinCompoundTag nbt = LinCompoundTag.builder()
                .put("front_text", LinCompoundTag.builder().put("messages", messages).build())
                .build();

        LinListTag<LinStringTag> cleaned = cleaner.clean(nbt)
                .getTag("front_text", LinTagType.compoundTag())
                .getListTag("messages", LinTagType.stringTag());

        assertEquals(1, cleaned.value().size());
        assertEquals(List.of("messages"), removedTexts);
    }

    @Test
    void reportsEachRemovedTextOnce() {
        LinCompoundTag nbt = LinCompoundTag.builder().putString("Command", "clickEvent run_command").build();

        assertTrue(cleaner.clean(nbt).value().isEmpty());
        assertEquals(List.of("Command"), removedTexts);
    }
}
