package org.transmartproject.common.type;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.HashMap;
import java.util.Map;

public enum QueryStatus {
    Processing,
    Finished,
    Error,
    Incomplete,
    Completed;

    @JsonValue
    String getName() {
        return this.name().toUpperCase();
    }

    private static final Map<String, QueryStatus> mapping = new HashMap<>();
    static {
        for (QueryStatus status: values()) {
            mapping.put(status.name().toLowerCase(), status);
        }
    }

    @JsonCreator
    public static QueryStatus forName(String name) {
        if (name == null) {
            throw new IllegalArgumentException("Query status not specified");
        }
        name = name.toLowerCase();
        if (mapping.containsKey(name)) {
            return mapping.get(name);
        } else {
            throw new IllegalArgumentException(String.format("Query status not supported: %s", name));
        }
    }
}
