package org.transmartproject.common.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class Cell {
    private List<Object> inlineDimensions;
    private List<Integer> dimensionIndexes;
    private BigDecimal numericValue;
    private String stringValue;
}
