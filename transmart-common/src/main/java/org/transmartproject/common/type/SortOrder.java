package org.transmartproject.common.type;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.HashMap;
import java.util.Map;

public enum SortOrder {
    Asc,
    Desc,
    None;

    @JsonValue
    String getName() {
        return this.name().toLowerCase();
    }

    private static final Map<String, SortOrder> mapping = new HashMap<>();
    static {
        for (SortOrder type: values()) {
            mapping.put(type.name().toLowerCase(), type);
        }
    }

    @JsonCreator
    public static SortOrder forName(String name) {
        if (name == null) {
            return None;
        }
        name = name.toLowerCase();
        return mapping.getOrDefault(name, None);
    }
}
