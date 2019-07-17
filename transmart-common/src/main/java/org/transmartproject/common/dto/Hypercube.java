package org.transmartproject.common.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class Hypercube {
    private List<DimensionDeclaration> dimensionDeclarations;
    private List<SortDeclaration> sort;
    private List<Cell> cells;
    private Map<String, List<Object>> dimensionElements;
}
