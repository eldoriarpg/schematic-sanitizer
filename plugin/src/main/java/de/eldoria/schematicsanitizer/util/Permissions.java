/*
 *     SPDX-License-Identifier: LGPL-3.0-or-later
 *
 *     Copyright (C) RainbowDashLabs and Contributor
 */

package de.eldoria.schematicsanitizer.util;

public final class Permissions {
    private static final String BASE = "schematicsanitizer";
    public static final String DEBUG = String.join(".", BASE, "admin");

    private Permissions() {
        throw new UnsupportedOperationException("This is a utility class.");
    }

    private static String perm(String... perms) {
        return String.join(".", perms);
    }

    public static class Fix {
        private static final String FIX = perm(BASE, "fix");
        public static final String USE = perm(FIX, "use");
        public static final String BATCH = perm(FIX, "batch");
        private Fix() {
            throw new UnsupportedOperationException("This is a utility class.");
        }
    }

    public static class Check {
        private static final String CHECK = perm(BASE, "check");
        public static final String USE = perm(CHECK, "use");
        private Check() {
            throw new UnsupportedOperationException("This is a utility class.");
        }
    }
}
