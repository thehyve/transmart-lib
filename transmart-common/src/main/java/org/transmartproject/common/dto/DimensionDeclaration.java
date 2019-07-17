package org.transmartproject.common.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.transmartproject.common.type.DimensionType;
import org.transmartproject.common.type.ValueType;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class DimensionDeclaration {
    private @NotNull String name;
    private DimensionType dimensionType;
    private Integer sortIndex;
    private ValueType valueType;
    private List<Field> fields;
    private Boolean inline;
}
