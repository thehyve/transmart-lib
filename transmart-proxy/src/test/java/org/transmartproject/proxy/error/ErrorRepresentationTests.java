package org.transmartproject.proxy.error;

import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;
import org.junit.Test;
import org.transmartproject.common.dto.error.ErrorRepresentation;

public class ErrorRepresentationTests {

    @Test
    public void testErrorRepresentationEquals() {
        EqualsVerifier
            .forClass(ErrorRepresentation.class)
            .suppress(Warning.STRICT_INHERITANCE, Warning.NONFINAL_FIELDS)
            .verify();
    }

}
