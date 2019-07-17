package org.transmartproject.common.dto;

import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;
import org.junit.Test;

public class AggregateTests {

    @Test
    public void testCountsEquals() {
        EqualsVerifier
            .forClass(Counts.class)
            .suppress(Warning.STRICT_INHERITANCE, Warning.NONFINAL_FIELDS)
            .verify();
    }

    @Test
    public void testNumericalValueAggregatesEquals() {
        EqualsVerifier
            .forClass(NumericalValueAggregates.class)
            .suppress(Warning.STRICT_INHERITANCE, Warning.NONFINAL_FIELDS)
            .verify();
    }

    @Test
    public void testCategoricalValueAggregatesEquals() {
        EqualsVerifier
            .forClass(CategoricalValueAggregates.class)
            .suppress(Warning.STRICT_INHERITANCE, Warning.NONFINAL_FIELDS)
            .verify();
    }

}
