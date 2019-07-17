package org.transmartproject.common.validation;

import org.transmartproject.common.constraint.ConceptConstraint;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ConceptConstraintValidator implements ConstraintValidator<ValidConceptConstraint, ConceptConstraint> {

    @Override
    public void initialize(ValidConceptConstraint validConceptConstraint) {}

    @Override
    public boolean isValid(ConceptConstraint constraint, ConstraintValidatorContext constraintValidatorContext) {
        boolean conceptCodeProvided = constraint.getConceptCode() != null &&
            !constraint.getConceptCode().trim().isEmpty();
        boolean pathProvided = constraint.getPath() != null &&
            !constraint.getPath().trim().isEmpty();
        boolean conceptCodesProvided = constraint.getConceptCodes() != null &&
            !constraint.getConceptCodes().isEmpty();
        return (conceptCodeProvided && !conceptCodesProvided && !pathProvided
            || !conceptCodeProvided && conceptCodesProvided && !pathProvided
            || !conceptCodeProvided && !conceptCodesProvided && pathProvided);
    }

}
