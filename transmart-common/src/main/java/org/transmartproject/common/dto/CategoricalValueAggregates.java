package org.transmartproject.common.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

/**
 * Categorical value aggregates
 */
@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class CategoricalValueAggregates {
    /**
     * Keys are values and values are counts. e.g. {Female: 345, Male 321}
     */
    private Map<String, Integer> valueCounts;

    /**
     * Counts for values equal null.
     */
    private Integer nullValueCounts;

}
