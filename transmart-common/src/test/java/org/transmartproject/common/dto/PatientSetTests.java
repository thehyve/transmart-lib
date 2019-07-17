package org.transmartproject.common.dto;

import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;
import org.junit.Test;

public class PatientSetTests {

    @Test
    public void testPatientSetEquals() {
        EqualsVerifier
            .forClass(PatientSetResult.class)
            .suppress(Warning.STRICT_INHERITANCE, Warning.NONFINAL_FIELDS)
            .verify();
        EqualsVerifier
            .forClass(PatientSetList.class)
            .suppress(Warning.STRICT_INHERITANCE, Warning.NONFINAL_FIELDS)
            .verify();
    }

}
