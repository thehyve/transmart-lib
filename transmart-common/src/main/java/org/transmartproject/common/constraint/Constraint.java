package org.transmartproject.common.constraint;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

/**
 * Superclass of all constraint types. Constraints
 * can be created using the constructors of the subclasses.
 */
@JsonTypeInfo(
    use = JsonTypeInfo.Id.NAME,
    property = "type"
)
@JsonSubTypes({
    @JsonSubTypes.Type(value = TrueConstraint.class, name = "true"),
    @JsonSubTypes.Type(value = BiomarkerConstraint.class, name = "biomarker"),
    @JsonSubTypes.Type(value = ModifierConstraint.class, name = "modifier"),
    @JsonSubTypes.Type(value = FieldConstraint.class, name = "field"),
    @JsonSubTypes.Type(value = ValueConstraint.class, name = "value"),
    @JsonSubTypes.Type(value = TimeConstraint.class, name = "time"),
    @JsonSubTypes.Type(value = PatientSetConstraint.class, name = "patient_set"),
    @JsonSubTypes.Type(value = Negation.class, name = "negation"),
    @JsonSubTypes.Type(value = AndConstraint.class, name = "and"),
    @JsonSubTypes.Type(value = OrConstraint.class, name = "or"),
    @JsonSubTypes.Type(value = TemporalConstraint.class, name = "temporal"),
    @JsonSubTypes.Type(value = ConceptConstraint.class, name = "concept"),
    @JsonSubTypes.Type(value = StudyNameConstraint.class, name = "study_name"),
    @JsonSubTypes.Type(value = NullConstraint.class, name = "null"),
    @JsonSubTypes.Type(value = SubSelectionConstraint.class, name = "subselection"),
    @JsonSubTypes.Type(value = RelationConstraint.class, name = "relation")
})
public abstract class Constraint {}
