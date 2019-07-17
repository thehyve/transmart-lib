package org.transmartproject.common.resource;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.transmartproject.common.dto.Hypercube;
import org.transmartproject.common.dto.Query;
import org.transmartproject.common.constraint.Constraint;

import javax.validation.Valid;

/**
 * Resource for observations.
 * Specifies the endpoint URLs and data types for
 * observation clients and servers.
 */
@RequestMapping("/v2/observations")
public interface ObservationResource {

    /**
     * POST  /v2/observations : query observations.
     *
     * @param query the object that contains the constraint.
     * @return the ResponseEntity with status 200 (OK) and the hypercube representing the
     * observations that match the constraint.
     */
    @PostMapping
    ResponseEntity<Hypercube> query(@Valid @RequestBody Query query);

}
