package org.transmartproject.common.constraint;

import com.fasterxml.jackson.annotation.JsonTypeName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.transmartproject.common.type.DataType;
import org.transmartproject.common.type.Operator;
import org.transmartproject.common.validation.DataTypeValidation;

import javax.validation.constraints.AssertTrue;

@Data @Builder @NoArgsConstructor @AllArgsConstructor
@JsonTypeName("value")
public class ValueConstraint extends Constraint {
    private DataType valueType;
    private Operator operator;
    private Object value;

    @AssertTrue(message = "Only String or Numeric value type is allowed")
    boolean hasOrStringOrNumericValueType() {
        return valueType == DataType.String || valueType == DataType.Numeric;
    }

    @AssertTrue(message = "The type does not support the value")
    boolean hasValueOfRightType() {
        return DataTypeValidation.supportsValue(valueType, value, operator);
    }

    @AssertTrue(message = "The value type is not compatible with the operator")
    boolean hasValidOperatorForGivenValueType() {
        return DataTypeValidation.supportsType(operator, valueType);
    }

}
