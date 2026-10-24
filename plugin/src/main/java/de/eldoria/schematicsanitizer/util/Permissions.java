/*
 *     SPDX-License-Identifier: AGPL-3.0-only
 *
 *     Copyright (C) EldoriaRPG Team and Contributor
 */
package de.eldoria.schematicsanitizer.util;

public final class Permissions {
    private static final String BASE = "schematicsanitizer";
    public static final String DEBUG = String.join(".", BASE, "admin");
    public static final String UPDATES = String.join(".", BASE, "updates");

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
