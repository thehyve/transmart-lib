package org.transmartproject.common.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * The relation type describes the type of a relation.
 */
@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class RelationTypeList {
    private List<RelationType> relationTypes;
}
