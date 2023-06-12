package de.eldoria.schematicsanitizer.util;

public class Text {
    public static String numbers(int value, int max) {
        return "%s%,d/%,d".formatted(limitColor(value, max), value, max);
    }

    public static String limitColor(int value, int max) {
        return value <= max ? "<#27e31e>" : "<#f22424>";
    }

}
