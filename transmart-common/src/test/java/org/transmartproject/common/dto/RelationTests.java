package org.transmartproject.common.dto;

import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;
import org.junit.Test;

public class RelationTests {

    @Test
    public void testRelationEquals() {
        EqualsVerifier
            .forClass(Relation.class)
            .suppress(Warning.STRICT_INHERITANCE, Warning.NONFINAL_FIELDS)
            .verify();
    }

    @Test
    public void testRelationListEquals() {
        EqualsVerifier
            .forClass(RelationList.class)
            .suppress(Warning.STRICT_INHERITANCE, Warning.NONFINAL_FIELDS)
            .verify();
    }

}
