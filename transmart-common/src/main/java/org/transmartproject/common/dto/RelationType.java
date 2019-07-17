package org.transmartproject.common.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * The relation type describes the type of a relation.
 */
@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class RelationType {
    /**
     * A short, unique label for the relation type, e.g., 'SIB'.
     */
    @NotNull
    @Size(max = 200)
    private String label;

    /**
     * A description of the relation type, e.g., 'Sibling of'.
     */
    private String description;

    /**
     * A flag that indicates if relations of this type are necessarily symmetrical.
     */
    private Boolean symmetrical;

    /**
     * A flag that  indicates if relations of this type are necessarily biological,
     * e.g., parent or twin relations. Other relationships are, for instance, spouse or
     * caregiver relations, but also, for instance, siblings are not necessarily biologically related.
     */
    private Boolean biological;
}
