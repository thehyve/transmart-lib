package org.transmartproject.common.constraint;

import com.fasterxml.jackson.annotation.JsonTypeName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Data @Builder @NoArgsConstructor @AllArgsConstructor
@JsonTypeName("negation")
public class Negation extends Constraint {
    private @Valid @NotNull Constraint arg;
}
