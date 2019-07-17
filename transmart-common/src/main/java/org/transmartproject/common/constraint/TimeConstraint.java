package org.transmartproject.common.constraint;

import com.fasterxml.jackson.annotation.JsonTypeName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.transmartproject.common.type.DataType;
import org.transmartproject.common.type.Operator;
import org.transmartproject.common.validation.DataTypeValidation;

import javax.validation.Valid;
import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Data @Builder @NoArgsConstructor @AllArgsConstructor
@JsonTypeName("time")
public class TimeConstraint extends Constraint {
    private @Valid @NotNull Field field;
    private Operator operator;
    private List<Date> values;

    @AssertTrue(message = "Only Date type is allowed for this constraint")
    boolean hasDateType() {
        return field == null || field.getType() == DataType.Date;
    }

    @AssertTrue(message = "Value is not of date type")
    boolean hasValuesOfRightType() {
        return field == null || values == null ||
            values.stream().allMatch(value -> DataTypeValidation.supportsValue(field.getType(), value));
    }

    @AssertTrue(message = "The field type is not compatible with the operator")
    boolean hasValidOperatorForGivenFieldType() {
        return field == null ||
            DataTypeValidation.supportsType(operator, field.getType());
    }

    @AssertTrue(message = "Dates list contains null")
    boolean hasNoNullDates() {
        return values == null || values.stream().noneMatch(Objects::isNull);
    }

}
