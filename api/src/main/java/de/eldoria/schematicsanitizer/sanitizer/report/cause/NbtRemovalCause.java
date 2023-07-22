/*
 *     SPDX-License-Identifier: LGPL-3.0-or-later
 *
 *     Copyright (C) RainbowDashLabs and Contributor
 */

package de.eldoria.schematicsanitizer.sanitizer.report.cause;

/**
 * A cause for the removal of NBT tags.
 * Extends the {@link Cause} class.
 */

public abstract class NbtRemovalCause extends Cause {
    /**
     * Represents a cause for removing text from a blacklist.
     */
    public static final NbtRemovalCause TEXT_BLACKLIST = new NbtRemovalCause("Text blacklist") {
    };
    /**
     * Represents a removal cause for illegal tags.
     */
    public static final NbtRemovalCause ILLEGAL_TAG = new NbtRemovalCause("Illegal Tag") {
    };

    /**
     * Creates a new NbtRemovalCause with the specified name.
     *
     * @param name the name of the NbtRemovalCause
     */
    public NbtRemovalCause(String name) {
        super(name);
    }
}
