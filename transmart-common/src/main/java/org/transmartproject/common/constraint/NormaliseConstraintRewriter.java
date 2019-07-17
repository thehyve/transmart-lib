package org.transmartproject.common.constraint;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Normalise the constraint. E.g., eliminate double negation, merge nested
 * boolean operators, apply rewrite rules such as
 *  - a && (b && c) -> a && b && c
 *  - !!a -> a
 *  - a && true -> a
 *  - a || true -> true
 * Returns the normalised constraint.
 */
public class NormaliseConstraintRewriter extends ConstraintRewriter {

    /**
     * Eliminate subselect true
     */
    @Override
    public Constraint build(SubSelectionConstraint constraint) {
        Constraint subconstraint = build(constraint.getConstraint());
        if (subconstraint instanceof TrueConstraint) {
            return subconstraint;
        } else {
            return new SubSelectionConstraint(constraint.getDimension(), subconstraint);
        }
    }

    /**
     * Eliminate double negation.
     */
    @Override
    public Constraint build(Negation constraint) {
        if (constraint.getArg() != null && constraint.getArg() instanceof Negation) {
            return build(((Negation)constraint.getArg()).getArg());
        } else {
            return new Negation(build(constraint.getArg()));
        }
    }

    /**
     * Combine nested combination constraints, simplify singleton combinations,
     * simplify disjunctions with true branches, and remove unneeded true constraints.
     */
    @Override
    public Constraint build(AndConstraint constraint) {
        List<Constraint> normalisedArgs = new ArrayList<>();
        List<Constraint> args = buildList(constraint.getArgs());
        for (Constraint arg: args) {
            if (arg instanceof AndConstraint) {
                normalisedArgs.addAll(((AndConstraint)arg).getArgs());
            } else if (arg instanceof TrueConstraint) {
                // skip
            } else {
                normalisedArgs.add(arg);
            }
        }
        if (normalisedArgs.size() == 1) {
            // if the combination has a single argument, that argument is returned instead.
            return normalisedArgs.get(0);
        }
        return new AndConstraint(normalisedArgs);
    }

    /**
     * Combine nested combination constraints, simplify singleton combinations,
     * simplify disjunctions with true branches, and remove unneeded true constraints.
     */
    @Override
    public Constraint build(OrConstraint constraint) {
        List<Constraint> normalisedArgs = new ArrayList<>();
        List<Constraint> args = buildList(constraint.getArgs());
        for (Constraint arg: args) {
            if (arg instanceof OrConstraint) {
                normalisedArgs.addAll(((OrConstraint)arg).getArgs());
            } else if (arg instanceof TrueConstraint) {
                // disjunction with true constraint as argument is equal to the true constraint
                return new TrueConstraint();
            } else {
                normalisedArgs.add(arg);
            }
        }
        if (normalisedArgs.size() == 1) {
            // if the combination has a single argument, that argument is returned instead.
            return normalisedArgs.get(0);
        }
        return new OrConstraint(normalisedArgs);
    }

    /**
     * Choose only conceptCode as filter if multiple values are available.
     */
    @Override
    public Constraint build(ConceptConstraint constraint) {
        if (constraint.getConceptCode() != null) {
            return ConceptConstraint.builder().conceptCode(constraint.getConceptCode()).build();
        } else if (constraint.getConceptCodes() != null && !constraint.getConceptCodes().isEmpty()) {
            if (constraint.getConceptCodes().size() == 1) {
                return ConceptConstraint.builder().conceptCode(constraint.getConceptCodes().get(0)).build();
            } else {
                return ConceptConstraint.builder()
                    .conceptCodes(
                        constraint.getConceptCodes().stream().sorted().collect(Collectors.toList()))
                    .build();
            }
        } else {
            return ConceptConstraint.builder().path(constraint.getPath()).build();
        }
    }

    @Override
    public Constraint build(PatientSetConstraint constraint) {
        if (constraint.getPatientSetId() != null) {
            return PatientSetConstraint.builder()
                .patientSetId(constraint.getPatientSetId())
                .offset(constraint.getOffset())
                .limit(constraint.getLimit())
                .build();
        } else if (constraint.getPatientIds() != null && !constraint.getPatientIds().isEmpty()) {
            return PatientSetConstraint.builder()
                .patientIds(constraint.getPatientIds().stream().sorted().collect(Collectors.toCollection(LinkedHashSet::new)))
                .build();
        } else {
            return PatientSetConstraint.builder()
                .subjectIds(constraint.getSubjectIds().stream().sorted().collect(Collectors.toCollection(LinkedHashSet::new)))
                .build();
        }
    }

}
