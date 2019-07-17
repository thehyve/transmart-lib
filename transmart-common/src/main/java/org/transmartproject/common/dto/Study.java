package org.transmartproject.common.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class Study {
    private Long id;
    private @NotNull String studyId;
    private Long bioExperimentId;
    private String secureObjectToken;
    private List<String> dimensions;
}
