package de.eldoria.schematicsanitizer.sanitizer.report.builder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BaseReportBuilder<V> {
    private final List<V> removed = new ArrayList<>();

    public void removed(V removed) {
        this.removed.add(removed);
    }

    public List<V> removed() {
        return Collections.unmodifiableList(removed);
    }
}
