package org.transmartproject.common.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class ConceptDimensionElement {
    private @NotBlank String conceptCode;
    private @NotBlank String name;
    private @NotBlank String conceptPath;
}
