package de.eldoria.schematicsanitizer.sanitizer.report.cause;

/**
 * This is an abstract class representing a cause.
 */

public abstract class Cause {
    private final String name;

    /**
     * Creates a new Cause object with the given name.
     *
     * @param name the name of the cause
     */
    public Cause(String name) {
        this.name = name;
    }

    /**
     * Returns a string representation of the Cause object.
     *
     * @return the name of the cause
     */
    @Override
    public String toString() {
        return name;
    }

}
