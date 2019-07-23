package org.transmartproject.common.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class StudyDimensionElement {
    private @NotNull String name;
}
