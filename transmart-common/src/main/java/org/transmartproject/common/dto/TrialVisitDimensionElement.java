package org.transmartproject.common.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class TrialVisitDimensionElement {
    private @NotBlank String relTimeLabel;
    private String relTimeUnit;
    private Integer relTime;
    private @NotBlank String studyId;
}
