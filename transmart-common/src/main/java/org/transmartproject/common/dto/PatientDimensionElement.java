package org.transmartproject.common.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.transmartproject.common.type.Sex;

import javax.validation.constraints.NotNull;
import java.util.Map;

@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class PatientDimensionElement {
    private @NotNull Long id;
    private Sex sex;
    private Map<String, String> subjectIds;
}
