package de.eldoria.schematicsanitizer.sanitizer.filter.builder;

import de.eldoria.schematicsanitizer.sanitizer.filter.Filter;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;

public class FilterBuilder {
    private final BlockFilterBuilder blockFilter = new BlockFilterBuilder();
    private final EntityFilterBuilder entityFilter = new EntityFilterBuilder();
    private final Set<String> textBlacklist = new HashSet<>();
    private final Set<String> nbtBlacklist = new HashSet<>();

    public FilterBuilder blockFilter(Consumer<BlockFilterBuilder> filter) {
        filter.accept(blockFilter);
        return this;
    }

    public FilterBuilder entityFilter(Consumer<EntityFilterBuilder> filter) {
        filter.accept(entityFilter);
        return this;
    }


    public FilterBuilder withTextBlacklist(String... text) {
        return withTextBlacklist(List.of(text));
    }

    public FilterBuilder withTextBlacklist(List<String> text) {
        textBlacklist.addAll(text);
        return this;
    }

    public FilterBuilder withNbtBlacklist(String... text) {
        return withNbtBlacklist(List.of(text));
    }

    public FilterBuilder withNbtBlacklist(List<String> text) {
        nbtBlacklist.addAll(text);
        return this;
    }

        public Filter build(){
        return new Filter(blockFilter.build(), entityFilter.build(), textBlacklist, nbtBlacklist);
    }


}
