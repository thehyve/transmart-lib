package org.transmartproject.common.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.transmartproject.common.type.SortOrder;

@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class SortDeclaration {
    private String dimension;
    private SortOrder sortOrder;
}
