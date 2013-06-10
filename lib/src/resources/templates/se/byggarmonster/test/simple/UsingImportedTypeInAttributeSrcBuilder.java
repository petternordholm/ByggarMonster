package se.byggarmonster.test.simple;

import java.util.List;

public class UsingImportedTypeInAttributeBuilder {
    private List<String> type;

    public UsingImportedTypeInAttribute build() {
        UsingImportedTypeInAttribute instance = new UsingImportedTypeInAttribute(type);
        return instance;
    }

    public UsingImportedTypeInAttributeBuilder from(final UsingImportedTypeInAttribute from) {
        UsingImportedTypeInAttributeBuilder instance = new UsingImportedTypeInAttributeBuilder();
        instance.type = from.getType();
        return instance;
    }

    public UsingImportedTypeInAttributeBuilder withType(final List<String> type) {
        this.type = type;
        return this;
    }

}
