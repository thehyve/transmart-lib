package org.transmartproject.common.type;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.HashMap;
import java.util.Map;

public enum VisualAttribute {
    /**
     * Non-terminal term that can be used as a query item.
     */
    Folder,

    /**
     * Non-terminal term that cannot be used as a query item.
     */
    Container,

    /**
     * The term represents several terms, which are collapsed on it. An
     * example is under Gender in the Demographics folder &#2013; the
     * term 'Unknown' having this type indicates that there are at least
     * two terms that are considered to be 'Unknown Gender' and both are
     * mapped to that one.
     */
    Multiple,

    /**
     * A terminal term.
     */
    Leaf,
    Modifier_container,
    Modifier_folder,
    Modifier_leaf,

    /**
     * A term to be displayed normally.
     */
    Active,

    /**
     * A term that cannot be used.
     */
    Inactive,

    /**
     * The term is hidden from the user.
     */
    Hidden,


    /**
     * If present, the term can have children added to it and it can also
     * be deleted.
     */
    Editable,

    /**
     * Indicates high-dimensional data.
     */
    High_dimensional,


    /**
     * Indicates numerical concepts.
     */
    Numerical,

    /**
     * Indicates concepts with textual values.
     */
    Text,

    /**
     * Indicates date concepts.
     */
    Date,

    /**
     * Indicates categorical concepts.
     */
    Categorical,

    /**
     * Indicates categorical values.
     */
    Categorical_option,

    /**
     * To indicate the term as study
     */
    Study,

    /**
     * To indicate the term as program
     */
    Program;

    @JsonValue
    String getName() {
        return this.name().toUpperCase();
    }

    private static final Map<String, VisualAttribute> mapping = new HashMap<>();
    static {
        for (VisualAttribute type: values()) {
            mapping.put(type.name().toLowerCase(), type);
        }
    }

    @JsonCreator
    public static VisualAttribute forName(String name) {
        if (name == null) {
            throw new IllegalArgumentException("Visual attribute not specified");
        }
        name = name.toLowerCase();
        if (mapping.containsKey(name)) {
            return mapping.get(name);
        } else {
            throw new IllegalArgumentException(
                java.lang.String.format("Visual attribute not supported: %s", name));
        }
    }

}
