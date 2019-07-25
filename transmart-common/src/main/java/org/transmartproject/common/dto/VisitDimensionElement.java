package org.transmartproject.common.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.time.ZonedDateTime;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class VisitDimensionElement {
    private @NotNull Long encounterNum;
    private @NotNull Long patientId;
    private String activeStatusCd;
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private ZonedDateTime startDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private ZonedDateTime endDate;
    private String inoutCd;
    private String locationCd;
    private Map<String, String> encounterIds;
}
