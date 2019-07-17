package org.transmartproject.common.dto;

import lombok.*;
import org.transmartproject.common.type.ValueType;

import javax.validation.constraints.NotNull;

@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class Field {
    private @NotNull String name;
    private ValueType type;
}
