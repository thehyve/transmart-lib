package org.transmartproject.common.constraint;

import com.fasterxml.jackson.annotation.JsonTypeName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data @Builder @NoArgsConstructor @AllArgsConstructor
@JsonTypeName("subselection")
public class SubSelectionConstraint extends Constraint {
    private @NotBlank String dimension;
    private @Valid @NotNull Constraint constraint;
}
