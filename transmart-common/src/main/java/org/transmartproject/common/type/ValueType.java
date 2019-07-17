package org.transmartproject.common.type;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.HashMap;
import java.util.Map;

public enum ValueType {
    String,
    Int,
    Double,
    Timestamp,
    Object;

    @JsonValue
    String getName() {
        return this.name().toUpperCase();
    }

    private static final Map<String, ValueType> mapping = new HashMap<>();
    static {
        for (ValueType type: values()) {
            mapping.put(type.name().toLowerCase(), type);
        }
    }

    @JsonCreator
    public static ValueType forName(String name) {
        name = name.toLowerCase();
        if (mapping.containsKey(name)) {
            return mapping.get(name);
        } else {
            throw new IllegalArgumentException(
                java.lang.String.format("Value type not supported: %s", name));
        }
    }
}
