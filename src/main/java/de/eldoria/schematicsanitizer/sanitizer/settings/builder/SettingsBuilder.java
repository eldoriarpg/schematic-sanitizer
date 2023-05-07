package de.eldoria.schematicsanitizer.sanitizer.settings.builder;

import de.eldoria.schematicsanitizer.sanitizer.filter.builder.FilterBuilder;
import de.eldoria.schematicsanitizer.sanitizer.limit.builder.LimitBuilder;
import de.eldoria.schematicsanitizer.sanitizer.settings.Settings;

import java.util.function.Consumer;

public class SettingsBuilder {
    private final FilterBuilder filter = new FilterBuilder();
    private final LimitBuilder limit = new LimitBuilder();

    public SettingsBuilder filter(Consumer<FilterBuilder> filter) {
        filter.accept(this.filter);
        return this;
    }

    public SettingsBuilder limit(Consumer<LimitBuilder> limit) {
        limit.accept(this.limit);
        return this;
    }

    public Settings build() {
        return new Settings(filter.build(), limit.build());
    }
}
