package org.transmartproject.common.resource;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.transmartproject.common.dto.CategoricalValueAggregates;
import org.transmartproject.common.dto.ConstraintParameter;
import org.transmartproject.common.dto.Counts;
import org.transmartproject.common.dto.NumericalValueAggregates;

import javax.validation.Valid;
import java.util.Map;

/**
 * Resource for aggregates based on observations.
 * Specifies the endpoint URLs and data types for
 * aggregate clients and servers.
 */
@RequestMapping("/v2/observations")
public interface AggregateResource {

    /**
     * POST  /v2/observations/counts
     *
     * Observation and patient counts: counts the number of observations that satisfy the constraint and that
     * the user has access to, and the number of associated patients.
     *
     * @param constraint the constraint.
     * @return the number of observations and patients.
     */
    @PostMapping("/counts")
    ResponseEntity<Counts> counts(@Valid @RequestBody ConstraintParameter constraint);

    /**
     * POST  /v2/observations/counts_per_concept
     *
     * Observation and patient counts per concept:
     * counts the number of observations that satisfy the constraint and that
     * the user has access to, and the number of associated patients,
     * and groups them by concept code.
     *
     * @param constraint the object containing the constraint.
     * @return a map from concept code to the counts.
     */
    @PostMapping("/counts_per_concept")
    ResponseEntity<Map<String, Counts>> countsPerConcept(@Valid @RequestBody ConstraintParameter constraint);

    /**
     * POST  /v2/observations/counts_per_study
     *
     * Observation and patient counts per study:
     * counts the number of observations that satisfy the constraint and that
     * the user has access to, and the number of associated patients,
     * and groups them by study id.
     *
     * @param constraint the object containing the constraint.
     * @return a map from study id to the counts.
     */
    @PostMapping("/counts_per_study")
    ResponseEntity<Map<String, Counts>> countsPerStudy(@Valid @RequestBody ConstraintParameter constraint);

    /**
     * POST  /v2/observations/counts_per_study_and_concept
     *
     * Observation and patient counts per study and concept:
     * counts the number of observations that satisfy the constraint and that
     * the user has access to, and the number of associated patients,
     * and groups them by first study id and then concept code.
     *
     * @param constraint the object containing the constraint.
     * @return a map from study id to maps from concept code to the counts.
     */
    @PostMapping("/counts_per_study_and_concept")
    ResponseEntity<Map<String, Map<String, Counts>>> countsPerStudyAndConcept(@Valid @RequestBody ConstraintParameter constraint);

    /**
     * POST  /v2/observations/aggregates_per_numerical_concept
     *
     * Calculate numerical values aggregates
     *
     * @param constraint the object containing the constraint that specifies from which observations you want to collect statistics
     * @return a map where keys are concept keys and values are aggregates
     */
    @PostMapping("/aggregates_per_numerical_concept")
    ResponseEntity<Map<String, NumericalValueAggregates>> numericalValueAggregatesPerConcept(@Valid @RequestBody ConstraintParameter constraint);

    /**
     * POST  /v2/observations/aggregates_per_categorical_concept
     *
     * Calculate categorical values aggregates
     *
     * @param constraint the object containing the constraint that specifies from which observations you want to collect statistics
     * @return a map where keys are concept keys and values are aggregates
     */
    @PostMapping("/aggregates_per_categorical_concept")
    ResponseEntity<Map<String, CategoricalValueAggregates>> categoricalValueAggregatesPerConcept(@Valid @RequestBody ConstraintParameter constraint);

}
