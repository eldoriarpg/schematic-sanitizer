package de.eldoria.schematicsanitizer.sanitizer.report.cause;

public abstract class Cause {
    private final String name;

    public Cause(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }

}
