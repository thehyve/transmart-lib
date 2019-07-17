package org.transmartproject.common.constraint;

import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;
import org.junit.Test;
import org.transmartproject.common.dto.ConstraintParameter;
import org.transmartproject.common.dto.Query;

public class ConstraintTests {

    @Test
    public void testConstraintEquals() {
        EqualsVerifier
            .forClass(TrueConstraint.class)
            .suppress(Warning.STRICT_INHERITANCE, Warning.NONFINAL_FIELDS)
            .verify();
        EqualsVerifier
            .forClass(NullConstraint.class)
            .suppress(Warning.STRICT_INHERITANCE, Warning.NONFINAL_FIELDS)
            .verify();
        EqualsVerifier
            .forClass(AndConstraint.class)
            .suppress(Warning.STRICT_INHERITANCE, Warning.NONFINAL_FIELDS)
            .verify();
        EqualsVerifier
            .forClass(OrConstraint.class)
            .suppress(Warning.STRICT_INHERITANCE, Warning.NONFINAL_FIELDS)
            .verify();
        EqualsVerifier
            .forClass(Negation.class)
            .suppress(Warning.STRICT_INHERITANCE, Warning.NONFINAL_FIELDS)
            .verify();
        EqualsVerifier
            .forClass(BiomarkerConstraint.class)
            .suppress(Warning.STRICT_INHERITANCE, Warning.NONFINAL_FIELDS)
            .verify();
        EqualsVerifier
            .forClass(FieldConstraint.class)
            .suppress(Warning.STRICT_INHERITANCE, Warning.NONFINAL_FIELDS)
            .verify();
        EqualsVerifier
            .forClass(ValueConstraint.class)
            .suppress(Warning.STRICT_INHERITANCE, Warning.NONFINAL_FIELDS)
            .verify();
        EqualsVerifier
            .forClass(PatientSetConstraint.class)
            .suppress(Warning.STRICT_INHERITANCE, Warning.NONFINAL_FIELDS)
            .verify();
        EqualsVerifier
            .forClass(ConceptConstraint.class)
            .suppress(Warning.STRICT_INHERITANCE, Warning.NONFINAL_FIELDS)
            .verify();
        EqualsVerifier
            .forClass(FieldConstraint.class)
            .suppress(Warning.STRICT_INHERITANCE, Warning.NONFINAL_FIELDS)
            .verify();
        EqualsVerifier
            .forClass(SubSelectionConstraint.class)
            .suppress(Warning.STRICT_INHERITANCE, Warning.NONFINAL_FIELDS)
            .verify();
        EqualsVerifier
            .forClass(RelationConstraint.class)
            .suppress(Warning.STRICT_INHERITANCE, Warning.NONFINAL_FIELDS)
            .verify();
        EqualsVerifier
            .forClass(StudyNameConstraint.class)
            .suppress(Warning.STRICT_INHERITANCE, Warning.NONFINAL_FIELDS)
            .verify();
        EqualsVerifier
            .forClass(TimeConstraint.class)
            .suppress(Warning.STRICT_INHERITANCE, Warning.NONFINAL_FIELDS)
            .verify();
        EqualsVerifier
            .forClass(TemporalConstraint.class)
            .suppress(Warning.STRICT_INHERITANCE, Warning.NONFINAL_FIELDS)
            .verify();
        EqualsVerifier
            .forClass(ModifierConstraint.class)
            .suppress(Warning.STRICT_INHERITANCE, Warning.NONFINAL_FIELDS)
            .verify();
        EqualsVerifier
            .forClass(ConstraintParameter.class)
            .suppress(Warning.STRICT_INHERITANCE, Warning.NONFINAL_FIELDS)
            .verify();
        EqualsVerifier
            .forClass(Query.class)
            .suppress(Warning.STRICT_INHERITANCE, Warning.NONFINAL_FIELDS)
            .verify();
    }

}
