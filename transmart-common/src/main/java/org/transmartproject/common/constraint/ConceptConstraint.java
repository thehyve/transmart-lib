package org.transmartproject.common.constraint;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonTypeName;
import lombok.*;
import org.transmartproject.common.validation.ValidConceptConstraint;

import java.util.List;

@Data @Builder @NoArgsConstructor @AllArgsConstructor
@JsonTypeName("concept")
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@ValidConceptConstraint
public class ConceptConstraint extends Constraint {
    private String conceptCode;
    private List<String> conceptCodes;
    private String path;
}
