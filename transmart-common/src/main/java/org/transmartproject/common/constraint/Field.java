package org.transmartproject.common.constraint;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.transmartproject.common.type.DataType;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotBlank;

@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class Field {
    private String dimension;
    private @NotBlank String fieldName;
    private DataType type;

    @AssertTrue(message = "None type is not allowed")
    boolean hasType() {
        return type != DataType.None;
    }

}
