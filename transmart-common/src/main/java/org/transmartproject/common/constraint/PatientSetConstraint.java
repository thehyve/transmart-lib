package org.transmartproject.common.constraint;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonTypeName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.transmartproject.common.validation.ValidPatientSetConstraint;

import java.util.Set;

@Data @Builder @NoArgsConstructor @AllArgsConstructor
@JsonTypeName("patient_set")
@JsonInclude(JsonInclude.Include.NON_NULL)
@ValidPatientSetConstraint
public class PatientSetConstraint extends Constraint {
    private Long patientSetId;
    private Set<Long> patientIds;
    private Set<String> subjectIds;

    private Integer offset;
    private Integer limit;
}
