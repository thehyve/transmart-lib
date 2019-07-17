package org.transmartproject.common.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class Counts {
    /**
     * Number of patients. -1 when is not known. Could be -2 when the patient count is below the threshold.
     */
    private long patientCount;
    /**
     * Number of observations. -1 when is not known. -2 when patient count is -2.
     */
    private @Builder.Default long observationCount = -1;
}
