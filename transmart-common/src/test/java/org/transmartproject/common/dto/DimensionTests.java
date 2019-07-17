package org.transmartproject.common.dto;

import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;
import org.junit.Assert;
import org.junit.Test;
import org.transmartproject.common.type.DimensionType;

import java.util.Arrays;
import java.util.List;

public class DimensionTests {

    @Test
    public void testDimensionEquals() {
        EqualsVerifier
            .forClass(Dimension.class)
            .suppress(Warning.STRICT_INHERITANCE, Warning.NONFINAL_FIELDS)
            .verify();
    }

    @Test
    public void testDimensionBuilder() {
        Dimension expected = new Dimension("patient", DimensionType.Subject, null, null, null);
        Assert.assertEquals(expected, Dimension.builder().name("patient").dimensionType(DimensionType.Subject).build());
    }

    @Test
    public void testDimensionListEquals() {
        EqualsVerifier
            .forClass(DimensionList.class)
            .suppress(Warning.STRICT_INHERITANCE, Warning.NONFINAL_FIELDS)
            .verify();
    }

    @Test
    public void testDimensionListBuilder() {
        List<Dimension> dimensions = Arrays.asList(
            Dimension.builder().name("patient").build(),
            Dimension.builder()
                .name("concept")
                .build());
        DimensionList expected = new DimensionList(dimensions);
        Assert.assertEquals(expected, DimensionList.builder().dimensions(dimensions).build());
    }

}
