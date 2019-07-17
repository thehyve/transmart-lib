package org.transmartproject.common.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Patient set constraint should contain exactly one of 'patientSetId', 'patientIds' or 'subjectIds'.
 */
@Target({ElementType.TYPE, ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PatientSetConstraintValidator.class)
public @interface ValidPatientSetConstraint {
    String message() default "Patient set constraint should contain exactly one of 'patientSetId', 'patientIds' or 'subjectIds'.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
