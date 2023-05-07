package de.eldoria.schematicsanitizer.sanitizer.limit.builder;

import de.eldoria.schematicsanitizer.sanitizer.limit.Limit;

import java.util.function.Consumer;

public class LimitBuilder {
    private int size = 600;
    private final ContentLimitBuilder contentLimit= new ContentLimitBuilder();

    public LimitBuilder size(int size) {
        this.size = size;
        return this;
    }

    public LimitBuilder contentLimit(Consumer<ContentLimitBuilder> limit){
        limit.accept(contentLimit);
        return this;
    }

    public Limit build(){
        return new Limit(size, contentLimit.build());
    }
}
