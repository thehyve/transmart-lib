package org.transmartproject.proxy.server;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;
import org.transmartproject.common.dto.Hypercube;
import org.transmartproject.common.dto.Query;
import org.transmartproject.common.constraint.Constraint;
import org.transmartproject.common.resource.ObservationResource;
import org.transmartproject.proxy.security.CurrentUser;
import org.transmartproject.proxy.service.ObservationClientService;

/**
 * Proxy server for observations.
 */
@RestController
@Validated
@CrossOrigin
@Api(value="observations", description="Observations")
public class ObservationProxyServer implements ObservationResource {

    private Logger log = LoggerFactory.getLogger(ObservationProxyServer.class);

    private ObservationClientService observationClientService;

    ObservationProxyServer(ObservationClientService observationClientService) {
        log.info("Observation proxy server initialised.");
        this.observationClientService = observationClientService;
    }

    @Override
    @ApiOperation(value = "List all observations matching the constraint", produces = "application/json")
    public ResponseEntity<Hypercube> query(Query query) {
        log.info("Query for user {}. Query: {}", CurrentUser.getLogin(), query);
        return ResponseEntity.ok(observationClientService.fetchObservations(query));
    }

}
