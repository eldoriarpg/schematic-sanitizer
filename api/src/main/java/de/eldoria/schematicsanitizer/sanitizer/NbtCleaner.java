/*
 *     SPDX-License-Identifier: LGPL-3.0-or-later
 *
 *     Copyright (C) RainbowDashLabs and Contributor
 */

package de.eldoria.schematicsanitizer.sanitizer;

import de.eldoria.schematicsanitizer.sanitizer.filter.Filter;
import org.enginehub.linbus.tree.LinCompoundTag;
import org.enginehub.linbus.tree.LinListTag;
import org.enginehub.linbus.tree.LinStringTag;
import org.enginehub.linbus.tree.LinTag;
import org.jetbrains.annotations.Nullable;

import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

/**
 * Removes blacklisted keys and texts from nbt data as defined by {@link Filter#nbtBlacklist()} and
 * {@link Filter#textBlacklist()}.
 * <p>
 * Nbt tags are immutable, so the cleaner never modifies its input and always returns a rebuilt copy.
 * Compounds and lists are searched at any depth, which covers texts stored in lists like sign messages or item lore.
 */
final class NbtCleaner {
    private final Set<String> nbtBlacklist;
    private final Set<String> textBlacklist;
    private final Consumer<String> onIllegalTag;
    private final BiConsumer<String, String> onIllegalText;

    /**
     * Creates a new cleaner.
     *
     * @param nbtBlacklist  keys which are removed together with their value
     * @param textBlacklist texts which cause a string tag containing them to be removed
     * @param onIllegalTag  called with the key of every removed blacklisted tag
     * @param onIllegalText called with the key and the value of every removed string tag
     */
    NbtCleaner(Set<String> nbtBlacklist, Set<String> textBlacklist,
               Consumer<String> onIllegalTag, BiConsumer<String, String> onIllegalText) {
        this.nbtBlacklist = nbtBlacklist;
        this.textBlacklist = textBlacklist;
        this.onIllegalTag = onIllegalTag;
        this.onIllegalText = onIllegalText;
    }

    /**
     * Creates a cleaner using the blacklists of the given filter.
     *
     * @param filter        filter providing the blacklists
     * @param onIllegalTag  called with the key of every removed blacklisted tag
     * @param onIllegalText called with the key and the value of every removed string tag
     * @return a new cleaner
     */
    static NbtCleaner of(Filter filter, Consumer<String> onIllegalTag, BiConsumer<String, String> onIllegalText) {
        return new NbtCleaner(filter.nbtBlacklist(), filter.textBlacklist(), onIllegalTag, onIllegalText);
    }

    /**
     * Returns a copy of the compound with all blacklisted keys and texts removed.
     *
     * @param compound the compound to clean
     * @return the cleaned copy
     */
    LinCompoundTag clean(LinCompoundTag compound) {
        LinCompoundTag.Builder builder = LinCompoundTag.builder();
        compound.value().forEach((key, value) -> {
            LinTag<?> cleaned = cleanEntry(key, value);
            if (cleaned != null) builder.put(key, cleaned);
        });
        return builder.build();
    }

    @Nullable
    private LinTag<?> cleanEntry(String key, LinTag<?> value) {
        if (nbtBlacklist.contains(key)) {
            onIllegalTag.accept(key);
            return null;
        }
        return cleanValue(key, value);
    }

    @Nullable
    private LinTag<?> cleanValue(String key, LinTag<?> value) {
        if (value instanceof LinStringTag text && containsBlacklistedText(text.value())) {
            onIllegalText.accept(key, text.value());
            return null;
        }
        if (value instanceof LinCompoundTag compound) return clean(compound);
        if (value instanceof LinListTag<?> list) return cleanList(key, list);
        return value;
    }

    @SuppressWarnings("unchecked")
    private <T extends LinTag<?>> LinListTag<T> cleanList(String key, LinListTag<T> list) {
        LinListTag.Builder<T> builder = LinListTag.builder(list.elementType());
        for (T element : list.value()) {
            LinTag<?> cleaned = cleanValue(key, element);
            if (cleaned != null) builder.add((T) cleaned);
        }
        return builder.build();
    }

    private boolean containsBlacklistedText(String text) {
        return textBlacklist.stream().anyMatch(text::contains);
    }
}
