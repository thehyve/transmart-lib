package org.transmartproject.common.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.transmartproject.common.constraint.Constraint;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class ConstraintParameter {
    private @Valid @NotNull Constraint constraint;
}
