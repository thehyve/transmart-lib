package org.transmartproject.common.constraint;

import com.fasterxml.jackson.annotation.JsonTypeName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data @Builder @NoArgsConstructor @AllArgsConstructor
@JsonTypeName("study_name")
public class StudyNameConstraint extends Constraint {
    private @NotBlank String studyId;
}
