package org.transmartproject.common.constraint;

import com.fasterxml.jackson.annotation.JsonTypeName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data @Builder @NoArgsConstructor @AllArgsConstructor
@JsonTypeName("or")
public class OrConstraint extends Constraint {
    private @Valid @NotNull List<Constraint> args;
}
