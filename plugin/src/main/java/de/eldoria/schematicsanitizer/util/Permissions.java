package de.eldoria.schematicsanitizer.util;

public final class Permissions {
    private Permissions() {
        throw new UnsupportedOperationException("This is a utility class.");
    }

    private static final String BASE = "schematicsanitizer";
    public static class Fix {
        private Fix() {
            throw new UnsupportedOperationException("This is a utility class.");
        }

        private static final String FIX = perm(BASE, "fix");
        public static final String USE = perm(FIX, "use");
        public static final String BATCH = perm(FIX, "batch");
    }

    public static class Check {
        private Check() {
            throw new UnsupportedOperationException("This is a utility class.");
        }

        private static final String CHECK = perm(BASE, "check");
        public static final String USE = perm(CHECK, "use");
    }

    public static final String DEBUG = String.join(".", BASE, "admin");

    private static String perm(String... perms) {
        return String.join(".", perms);
    }
}
