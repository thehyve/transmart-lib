package org.transmartproject.common.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.transmartproject.common.type.DimensionType;
import org.transmartproject.common.type.ValueType;

import javax.validation.constraints.NotNull;

@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class Dimension {
    private @NotNull String name;
    private DimensionType dimensionType;
    private Integer sortIndex;
    private ValueType valueType;
    private String modifierCode;
}
