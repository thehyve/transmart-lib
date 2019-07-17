package org.transmartproject.common.constraint;

import java.util.List;
import java.util.stream.Collectors;

public abstract class ConstraintBuilder<T> {

    public List<T> buildList(List<Constraint> constraints) {
        return constraints.stream().map(this::build).collect(Collectors.toList());
    }

    public abstract T build(TrueConstraint constraint);

    public abstract T build(Negation constraint);

    public abstract T build(AndConstraint constraint);

    public abstract T build(OrConstraint constraint);

    public abstract T build(SubSelectionConstraint constraint);

    public abstract T build(NullConstraint constraint);

    public abstract T build(BiomarkerConstraint constraint);

    public abstract T build(ModifierConstraint constraint);

    public abstract T build(FieldConstraint constraint);

    public abstract T build(ValueConstraint constraint);

    public abstract T build(TimeConstraint constraint);

    public abstract T build(PatientSetConstraint constraint);

    public abstract T build(TemporalConstraint constraint);

    public abstract T build(ConceptConstraint constraint);

    public abstract T build(StudyNameConstraint constraint);

    public abstract T build(RelationConstraint constraint);

    public T build(Constraint constraint) {
        if (constraint instanceof TrueConstraint) {
            return build((TrueConstraint) constraint);
        } else if (constraint instanceof Negation) {
            return build((Negation) constraint);
        } else if (constraint instanceof  AndConstraint) {
            return build((AndConstraint) constraint);
        } else if (constraint instanceof  OrConstraint) {
            return build((OrConstraint) constraint);
        } else if (constraint instanceof  SubSelectionConstraint) {
            return build((SubSelectionConstraint) constraint);
        } else if (constraint instanceof  NullConstraint) {
            return build((NullConstraint) constraint);
        } else if (constraint instanceof  BiomarkerConstraint) {
            return build((BiomarkerConstraint) constraint);
        } else if (constraint instanceof  ModifierConstraint) {
            return build((ModifierConstraint) constraint);
        } else if (constraint instanceof  FieldConstraint) {
            return build((FieldConstraint) constraint);
        } else if (constraint instanceof  ValueConstraint) {
            return build((ValueConstraint) constraint);
        } else if (constraint instanceof  TimeConstraint) {
            return build((TimeConstraint) constraint);
        } else if (constraint instanceof  PatientSetConstraint) {
            return build((PatientSetConstraint) constraint);
        } else if (constraint instanceof  TemporalConstraint) {
            return build((TemporalConstraint) constraint);
        } else if (constraint instanceof  ConceptConstraint) {
            return build((ConceptConstraint) constraint);
        } else if (constraint instanceof  StudyNameConstraint) {
            return build((StudyNameConstraint) constraint);
        } else if (constraint instanceof  RelationConstraint){
            return build((RelationConstraint) constraint);
        }
        throw new IllegalArgumentException(
            String.format("Constraint type not supported: %s.", constraint.getClass()));
    }

}
