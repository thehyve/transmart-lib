package org.transmartproject.common.constraint;

public class ConstraintRewriter extends ConstraintBuilder<Constraint> {

    @Override
    public Constraint build(TrueConstraint constraint) {
        return constraint;
    }

    @Override
    public Constraint build(Negation constraint) {
        return new Negation(build(constraint.getArg()));
    }

    @Override
    public Constraint build(AndConstraint constraint) {
        return new AndConstraint(buildList(constraint.getArgs()));
    }

    @Override
    public Constraint build(OrConstraint constraint) {
        return new OrConstraint(buildList(constraint.getArgs()));
    }

    @Override
    public Constraint build(SubSelectionConstraint constraint) {
        return new SubSelectionConstraint(constraint.getDimension(),
            build(constraint.getConstraint()));
    }

    @Override
    public Constraint build(NullConstraint constraint) {
        return constraint;
    }

    @Override
    public Constraint build(BiomarkerConstraint constraint) {
        return constraint;
    }

    @Override
    public Constraint build(ModifierConstraint constraint) {
        return constraint;
    }

    @Override
    public Constraint build(FieldConstraint constraint) {
        return constraint;
    }

    @Override
    public Constraint build(ValueConstraint constraint) {
        return constraint;
    }

    @Override
    public Constraint build(TimeConstraint constraint) {
        return constraint;
    }

    @Override
    public Constraint build(PatientSetConstraint constraint) {
        return constraint;
    }

    @Override
    public Constraint build(TemporalConstraint constraint) {
        return new TemporalConstraint(constraint.getOperator(), build(constraint.getEventConstraint()));
    }

    @Override
    public Constraint build(ConceptConstraint constraint) {
        return constraint;
    }

    @Override
    public Constraint build(StudyNameConstraint constraint) {
        return constraint;
    }

    @Override
    public Constraint build(RelationConstraint constraint) {
        return new RelationConstraint(constraint.getRelationTypeLabel(),
            constraint.getRelatedSubjectsConstraint() != null ? build(constraint.getRelatedSubjectsConstraint()) : null,
            constraint.getBiological(), constraint.getShareHousehold());
    }

}
