package org.transmartproject.common.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Numerical values aggregates
 */
@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class NumericalValueAggregates {
    private Double min;
    private Double max;
    private Double avg;
    private Integer count;
    private Double stdDev;
}
