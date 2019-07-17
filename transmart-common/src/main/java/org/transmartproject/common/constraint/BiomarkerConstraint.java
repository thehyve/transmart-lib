package org.transmartproject.common.constraint;

import com.fasterxml.jackson.annotation.JsonTypeName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.util.Map;

@Data @Builder @NoArgsConstructor @AllArgsConstructor
@JsonTypeName("biomarker")
public class BiomarkerConstraint extends Constraint {
    private @NotBlank String biomarkerType;
    private Map<String, Object> params;
}
