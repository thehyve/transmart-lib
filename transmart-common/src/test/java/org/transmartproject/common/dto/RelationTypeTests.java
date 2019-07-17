package org.transmartproject.common.dto;

import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;
import org.junit.Test;

public class RelationTypeTests {

    @Test
    public void testRelationTypeEquals() {
        EqualsVerifier
            .forClass(RelationType.class)
            .suppress(Warning.STRICT_INHERITANCE, Warning.NONFINAL_FIELDS)
            .verify();
    }

    @Test
    public void testRelationTypeListEquals() {
        EqualsVerifier
            .forClass(RelationTypeList.class)
            .suppress(Warning.STRICT_INHERITANCE, Warning.NONFINAL_FIELDS)
            .verify();
    }

}
