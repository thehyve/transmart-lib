package org.transmartproject.common.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Concept constraint should contain exactly one of 'conceptCode', 'conceptCodes' or 'path'.
 */
@Target({ElementType.TYPE, ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ConceptConstraintValidator.class)
public @interface ValidConceptConstraint {
    String message() default "Concept constraint should contain exactly one of 'conceptCode', 'conceptCodes' or 'path'.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
