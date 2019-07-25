package org.transmartproject.common.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.transmartproject.common.constraint.Constraint;
import org.transmartproject.common.type.QueryStatus;

import javax.validation.constraints.NotBlank;

@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class PatientSetResult {
    /**
     * The query result instance id.
     */
    private Long id;

    /**
     * The size of the set,
     * or -1 if there was an error,
     * or -2 if user does not have permission to the count.
     */
    private Long setSize;

    /**
     * The query name
     */
    private @NotBlank String name;

    /**
     * A description of the query, set by the creator.
     */
    private String description;

    /**
     * The status of this query result instance.
     */
    private QueryStatus status;

    /**
     * The error message associated with this query result. May be an
     * exception trace.
     */
    private String errorMessage;

    /**
     * The username of the user associated with this query. There may not
     * exist a user with this username anymore.
     */
    private String username;

    /**
     * The API version used to create the patient set.
     */
    private String apiVersion;

    /**
     * The XML definition of the query that was executed, if the patient set
     * was created using the v1 API.
     */
    private String queryXML;

    /**
     * The constraint that was executed, if the patient set was created using
     * the v2 API.
     */
    private String requestConstraints;
}
