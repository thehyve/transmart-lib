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

@Data @Builder @NoArgsConstructor @AllArgsConstructor
@JsonTypeName("temporal")
public class TemporalConstraint extends Constraint {
    private Operator operator;
    private @Valid @NotNull Constraint eventConstraint;

    @AssertTrue(message = "This operator does not support event type")
    boolean hasEventOperator() {
        return DataTypeValidation.supportsType(operator, DataType.Event);
    }

}
