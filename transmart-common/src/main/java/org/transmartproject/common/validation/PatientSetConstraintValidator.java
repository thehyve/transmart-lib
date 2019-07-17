package org.transmartproject.common.validation;

import org.transmartproject.common.constraint.PatientSetConstraint;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PatientSetConstraintValidator implements ConstraintValidator<ValidPatientSetConstraint, PatientSetConstraint> {

    @Override
    public void initialize(ValidPatientSetConstraint validPatientSetConstraint) {}

    @Override
    public boolean isValid(PatientSetConstraint constraint, ConstraintValidatorContext constraintValidatorContext) {
        boolean patientSetId = constraint.getPatientSetId() != null;
        boolean patientIds = constraint.getPatientIds() != null && !constraint.getPatientIds().isEmpty();
        boolean subjectIds = constraint.getSubjectIds() != null && !constraint.getSubjectIds().isEmpty();
        return (patientSetId && !patientIds && !subjectIds
            || !patientSetId && patientIds && !subjectIds
            || !patientSetId && !patientIds && subjectIds);
    }

}
