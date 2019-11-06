package org.transmartproject.common.type;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.HashMap;
import java.util.Map;

public enum Sex {
    Male,
    Female,
    Unknown;

    @JsonValue
    String getName() {
        return this.name().toLowerCase();
    }

    private static final Map<String, Sex> mapping = new HashMap<>();
    static {
        for (Sex type: values()) {
            mapping.put(type.name().toLowerCase(), type);
        }
    }

    @JsonCreator
    public static Sex forName(String name) {
        if (name == null) {
            return Unknown;
        }
        name = name.toLowerCase();
        return mapping.getOrDefault(name, Unknown);
    }
}
