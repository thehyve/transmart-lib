package org.transmartproject.common.type;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.HashMap;
import java.util.Map;

public enum Operator {
    Less_than("<"),
    Greater_than(">"),
    Equals("="),
    Not_equals("!="),
    Less_than_or_equals("<="),
    Greather_than_or_equals(">="),
    Like("like"),
    Contains("contains"),
    In("in"),
    Before("<-"),
    After("->"),
    Between("<-->"),
    And("and"),
    Or("or"),
    Not("not"),
    Exists("exists"),
    Intersect("intersect"),
    Union("union"),
    None("none");

    String symbol;

    Operator(String symbol) {
        this.symbol = symbol;
    }

    @JsonValue
    String getSymbol() {
        return this.symbol;
    }

    private static final Map<String, Operator> mapping = new HashMap<>();
    static {
        for (Operator op : values()) {
            mapping.put(op.symbol, op);
        }
    }

    @JsonCreator
    static Operator forSymbol(String symbol) {
        if (mapping.containsKey(symbol)) {
            return mapping.get(symbol);
        } else {
            throw new IllegalArgumentException(String.format("Operator not supported: %s", symbol));
        }
    }

}
