package org.transmartproject.common.dto;

import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;
import org.junit.Test;
import org.transmartproject.common.dto.error.ErrorRepresentation;

public class ErrorTests {

    @Test
    public void testErrorEquals() {
        EqualsVerifier
            .forClass(ErrorRepresentation.class)
            .suppress(Warning.STRICT_INHERITANCE, Warning.NONFINAL_FIELDS)
            .verify();
    }
}
