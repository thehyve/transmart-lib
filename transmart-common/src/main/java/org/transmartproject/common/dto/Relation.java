package org.transmartproject.common.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

/**
 * Represents relationships between subjects.
 */
@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class Relation {
    /**
     * Left subject in the relation.
     */
    private @NotNull Long leftSubjectId;

    /**
     * Type of the relation between left and right subjects.
     */
    private @NotNull String relationTypeLabel;

    /**
     * Right subject in the relation.
     */
    private @NotNull Long rightSubjectId;

    /**
     * A flag that indicates if relation between subjects is biological.
     */
    private Boolean biological;

    /**
     * A flag that indicates if subjects share household.
     */
    private Boolean shareHousehold;
}
