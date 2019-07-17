package org.transmartproject.common.constraint;

import com.fasterxml.jackson.annotation.JsonTypeName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.transmartproject.common.type.Operator;
import org.transmartproject.common.validation.DataTypeValidation;

import javax.validation.Valid;
import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotNull;
import java.util.Collection;

@Data @Builder @NoArgsConstructor @AllArgsConstructor
@JsonTypeName("field")
public class FieldConstraint extends Constraint {
    private @Valid @NotNull Field field;
    private @NotNull Operator operator;
    private @NotNull Object value;

    @AssertTrue(message = "Operator has to be specified")
    boolean hasOperator() {
        return operator != Operator.None;
    }

    @AssertTrue(message = "The field type does not support the value")
    boolean hasValueOfRightType() {
        return DataTypeValidation.supportsValue(field.getType(), value, operator);
    }

    @AssertTrue(message = "The field type is not compatible with the operator")
    boolean hasTypeThatMatchesOperator() {
        return DataTypeValidation.supportsType(operator, field.getType());
    }

    @AssertTrue(message = "List of values expected")
    boolean hasNotListOperatorOrListValue() {
        return !DataTypeValidation.operatesOnCollection(operator) || value instanceof Collection;
    }

    @AssertTrue(message = "Concept dimension not allowed in field constraints. Use a ConceptConstraint instead.")
    boolean hasNoConceptDimension() {
        return field.getDimension() == null || !field.getDimension().equals("concept");
    }

    @AssertTrue(message = "Study dimension not allowed in field constraints. Use a StudyConstraint instead.")
    boolean hasNoStudyDimension() {
        return field.getDimension() == null || !field.getDimension().equals("study");
    }

    @AssertTrue(message = "Field \"study\" of trial visit dimension not allowed in field constraints. Use a StudyConstraint instead.")
    boolean hasNoTrialVisitStudyField() {
        return field.getDimension() == null ||
            !(field.getDimension().equals("trial visit") && field.getFieldName().equals("study"));
    }

}
