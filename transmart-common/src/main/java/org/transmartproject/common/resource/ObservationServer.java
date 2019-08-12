package org.transmartproject.common.resource;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;
import org.transmartproject.common.dto.Query;

import javax.validation.Valid;

/**
 * Server for observations.
 * Specifies the endpoint URLs and data types for
 * observation servers.
 * The response type is a {@link StreamingResponseBody}.
 */
@RequestMapping("/v2/observations")
public interface ObservationServer {

    /**
     * POST  /v2/observations : query observations.
     *
     * @param query the object that contains the constraint.
     * @return the ResponseEntity with status 200 (OK) and the hypercube representing the
     * observations that match the constraint.
     */
    @PostMapping
    ResponseEntity<StreamingResponseBody> query(@Valid @RequestBody Query query);

}
