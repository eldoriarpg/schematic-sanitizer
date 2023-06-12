package de.eldoria.schematicsanitizer.sanitizer.report;

import de.eldoria.schematicsanitizer.sanitizer.report.entities.ComponentEntity;

import java.util.List;
import java.util.stream.Collectors;

public interface SizedReport<T extends ComponentEntity> {
    default int size() {
        return entities().size();
    }

    default boolean isEmpty() {
        return size() == 0;
    }

    List<T> entities();

    default List<T> page(int page, int size) {
        return entities().subList(Math.min(page * size, size() - 1), Math.min(size(), page * size + size));
    }

    default int pages(int size) {
        return (int) Math.ceil(entities().size() / (double) size);
    }

    default String pageComponent(int page, int size) {
        return page(page, size).stream().map(ComponentEntity::component).collect(Collectors.joining("\n"));
    }
}
