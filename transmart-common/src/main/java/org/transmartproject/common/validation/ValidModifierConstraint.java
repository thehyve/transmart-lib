package org.transmartproject.common.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Modifier constraint should contain exactly one of 'modifierCode', 'path' or 'dimensionName'.
 */
@Target({ElementType.TYPE, ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ModifierConstraintValidator.class)
public @interface ValidModifierConstraint {
    String message() default "Modifier constraint should contain exactly one of 'modifierCode', 'path' or 'dimensionName'.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
