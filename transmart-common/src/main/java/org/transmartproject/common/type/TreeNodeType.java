package org.transmartproject.common.type;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.HashMap;
import java.util.Map;

public enum TreeNodeType {
    Study,
    High_dimensional,
    Numeric,
    Categorical_option,
    Folder,
    Categorical,
    Date,
    Text,
    Modifier,
    Unknown;

    @JsonValue
    String getName() {
        return this.name().toUpperCase();
    }

    private static final Map<String, TreeNodeType> mapping = new HashMap<>();
    static {
        for (TreeNodeType type: values()) {
            mapping.put(type.name().toLowerCase(), type);
        }
    }

    @JsonCreator
    public static TreeNodeType forName(String name) {
        name = name.toLowerCase();
        return mapping.getOrDefault(name, Unknown);
    }
}
