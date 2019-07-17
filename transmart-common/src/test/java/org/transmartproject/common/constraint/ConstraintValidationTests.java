package org.transmartproject.common.constraint;

import org.junit.Assert;
import org.junit.Test;
import org.transmartproject.common.type.DataType;
import org.transmartproject.common.type.Operator;

import javax.validation.ValidationException;
import java.math.BigDecimal;
import java.util.*;

import static org.transmartproject.common.constraint.ValidationTestHelper.validate;

public class ConstraintValidationTests {

    @Test(expected = ValidationException.class)
    public void testInvalidValueConstraint() {
        validate(ValueConstraint.builder()
            .operator(Operator.In)
            .valueType(DataType.Numeric)
            .value("abc")
            .build());
    }

    @Test
    public void testValidStringValueConstraint() {
        validate(ValueConstraint.builder()
            .operator(Operator.Contains)
            .valueType(DataType.String)
            .value("abc")
            .build());
    }

    @Test
    public void testValidNumericValueConstraint() {
        validate(ValueConstraint.builder()
            .operator(Operator.Greather_than_or_equals)
            .valueType(DataType.Numeric)
            .value(new BigDecimal(12.3))
            .build());
    }

    @Test(expected = ValidationException.class)
    public void testInvalidDateValueConstraint() {
        validate(ValueConstraint.builder()
            .operator(Operator.Between)
            .valueType(DataType.Date)
            .value(Arrays.asList(new Date(), new Date()))
            .build());
    }

    @Test(expected = ValidationException.class)
    public void testInvalidTypeValueConstraint() {
        validate(ValueConstraint.builder()
            .operator(Operator.Equals)
            .valueType(DataType.String)
            .value(12345)
            .build());
    }

    @Test
    public void testValidTimeConstraint() {
        validate(TimeConstraint.builder()
            .field(org.transmartproject.common.constraint.Field.builder()
                .fieldName("startDate")
                .type(DataType.Date)
                .build())
            .operator(Operator.Between)
            .values(Arrays.asList(new Date(), new Date()))
            .build());
    }

    @Test(expected = ValidationException.class)
    public void testTimeConstraintWithoutField() {
        validate(TimeConstraint.builder()
            .operator(Operator.Between)
            .values(Arrays.asList(new Date(), new Date()))
            .build());
    }

    @Test(expected = ValidationException.class)
    public void testTimeConstraintWithNullValue() {
        List<Date> values = new ArrayList<>();
        values.add(null);
        validate(TimeConstraint.builder()
            .field(org.transmartproject.common.constraint.Field.builder()
                .fieldName("startDate")
                .type(DataType.Date)
                .build())
            .operator(Operator.Before)
            .values(values)
            .build());
    }

    @Test(expected = ValidationException.class)
    public void testTimeConstraintWithInvalidOperator() {
        validate(TimeConstraint.builder()
            .operator(Operator.Contains)
            .values(Arrays.asList(new Date()))
            .build());
    }

    @Test
    public void testValidConceptConstraint() {
        validate(ConceptConstraint.builder()
            .conceptCode("Test")
            .build());
    }

    @Test(expected = ValidationException.class)
    public void testInvalidConceptConstraint() {
        validate(ConceptConstraint.builder()
            .conceptCode("Test")
            .path("\\Test\\")
            .build());
    }

    @Test
    public void testValidModifierConstraint() {
        validate(ModifierConstraint.builder()
            .modifierCode("Test")
            .build());
    }

    @Test(expected = ValidationException.class)
    public void testInvalidModifierConstraint() {
        validate(ModifierConstraint.builder()
            .modifierCode("Test")
            .path("\\Test\\")
            .build());
    }

    @Test
    public void testValidPatientSetConstraint() {
        validate(PatientSetConstraint.builder()
            .patientSetId(123L)
            .build());
    }

    @Test(expected = ValidationException.class)
    public void testInvalidPatientSetConstraint() {
        validate(PatientSetConstraint.builder()
            .subjectIds(new HashSet<>(Arrays.asList("SUBJ1", "SUBJ2")))
            .patientIds(new HashSet<>(Arrays.asList(123L, 456L)))
            .build());
    }

    private Field patientIdField = Field.builder()
        .dimension("patient")
        .fieldName("patientId")
        .type(DataType.Numeric)
        .build();

    @Test
    public void testFieldConstraintClass() {
        // when:
        FieldConstraint constraint = FieldConstraint.builder()
            .field(patientIdField)
            .operator(Operator.Equals)
            .value(-101)
            .build();

        // then:
        Assert.assertTrue(constraint.hasOperator());
        Assert.assertTrue(constraint.hasTypeThatMatchesOperator());
        Assert.assertTrue(constraint.hasValueOfRightType());
        Assert.assertTrue(constraint.hasNotListOperatorOrListValue());
    }

    @Test(expected = ValidationException.class)
    public void testInvalidValueConstraintInFieldConstraints() {
        // when:
        FieldConstraint constraint =  FieldConstraint.builder()
            .field(patientIdField)
            .operator(Operator.Equals)
            .value("Invalid patient id")
            .build();

        // then:
        Assert.assertFalse(constraint.hasValueOfRightType());

        // when: 'validating the constraint'
        validate(constraint);

        // then: ValidationException is thrown
    }

    @Test(expected = ValidationException.class)
    public void testConceptDimensionIsRejectedInFieldConstraints() {
        // when:
        FieldConstraint constraint = FieldConstraint.builder()
            .field(Field.builder().dimension("concept").fieldName("conceptCode").type(DataType.String).build())
            .operator(Operator.Equals)
            .value("TEST")
            .build();

        // then:
        Assert.assertFalse(constraint.hasNoConceptDimension());

        // when: 'validating the constraint'
        validate(constraint);

        // then: 'An exception is thrown'
    }

    @Test(expected = ValidationException.class)
    public void testStudyDimensionIsRejectedInFieldConstraints() {
        // when:
        FieldConstraint constraint = FieldConstraint.builder()
            .field(Field.builder().dimension("study").fieldName("studyId").type(DataType.String).build())
            .operator(Operator.Equals)
            .value("TEST")
            .build();

        // then:
        Assert.assertFalse(constraint.hasNoStudyDimension());

        // when: 'validating the constraint'
        validate(constraint);

        // then: 'An exception is thrown'
    }

    @Test(expected = ValidationException.class)
    public void testStudyFieldIsRejectedInFieldConstraints() {
        // when:
        FieldConstraint constraint = FieldConstraint.builder()
            .field(Field.builder().dimension("trial visit").fieldName("study").type(DataType.String).build())
            .operator(Operator.Equals)
            .value("TEST")
            .build();

        // then:
        Assert.assertFalse(constraint.hasNoTrialVisitStudyField());

        // when: 'validating the constraint'
        validate(constraint);

        // then: 'An exception is thrown'
    }

}
