package org.transmartproject.common.type;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.HashMap;
import java.util.Map;

public enum DataType {
    Id,
    Numeric,
    Date,
    String,
    Text,
    Event,
    Object,
    Collection,
    Constraint,
    None;

    @JsonValue
    String getName() {
        return this.name().toUpperCase();
    }

    private static final Map<String, DataType> mapping = new HashMap<>();
    static {
        for (DataType type: values()) {
            mapping.put(type.name().toLowerCase(), type);
        }
    }

    @JsonCreator
    public static DataType forName(String name) {
        name = name.toLowerCase();
        return mapping.getOrDefault(name, None);
    }
}
