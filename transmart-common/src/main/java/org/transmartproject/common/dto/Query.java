package org.transmartproject.common.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.transmartproject.common.constraint.Constraint;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.List;

@Data @Builder @NoArgsConstructor @AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Query {
    private @Pattern(regexp = "clinical", message = "Only clinical queries are supported.") String type;
    private @Valid @NotNull Constraint constraint;
    private @Valid List<SortDeclaration> sort;
}
