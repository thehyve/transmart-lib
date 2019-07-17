package org.transmartproject.common.validation;

import org.transmartproject.common.constraint.ModifierConstraint;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ModifierConstraintValidator implements ConstraintValidator<ValidModifierConstraint, ModifierConstraint> {

    @Override
    public void initialize(ValidModifierConstraint validModifierConstraint) {}

    @Override
    public boolean isValid(ModifierConstraint constraint, ConstraintValidatorContext constraintValidatorContext) {
        boolean modifierCodeProvided = constraint.getModifierCode() != null &&
            !constraint.getModifierCode().trim().isEmpty();
        boolean pathProvided = constraint.getPath() != null &&
            !constraint.getPath().trim().isEmpty();
        boolean dimensionNameProvided = constraint.getDimensionName() != null &&
            !constraint.getDimensionName().trim().isEmpty();
        return (modifierCodeProvided && !dimensionNameProvided && !pathProvided
            || !modifierCodeProvided && dimensionNameProvided && !pathProvided
            || !modifierCodeProvided && !dimensionNameProvided && pathProvided);
    }

}
