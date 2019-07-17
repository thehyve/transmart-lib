package org.transmartproject.common.type;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.HashMap;
import java.util.Map;

public enum DimensionType {
    Subject,
    Attribute;

    @JsonValue
    String getName() {
        return this.name().toUpperCase();
    }

    private static final Map<String, DimensionType> mapping = new HashMap<>();
    static {
        for (DimensionType type: values()) {
            mapping.put(type.name().toLowerCase(), type);
        }
    }

    @JsonCreator
    public static DimensionType forName(String name) {
        name = name.toLowerCase();
        return mapping.getOrDefault(name, Attribute);
    }
}
