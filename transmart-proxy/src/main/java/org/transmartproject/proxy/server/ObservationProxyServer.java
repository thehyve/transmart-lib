package org.transmartproject.proxy.server;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.fileupload.util.Streams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;
import org.transmartproject.common.dto.Query;
import org.transmartproject.common.exception.ServiceNotAvailable;
import org.transmartproject.common.resource.ObservationServer;
import org.transmartproject.proxy.security.CurrentUser;
import org.transmartproject.proxy.service.ObservationClientService;

import java.io.IOException;

/**
 * Proxy server for observations.
 */
@RestController
@Validated
@CrossOrigin
@Api(value="observations", description="Observations")
public class ObservationProxyServer implements ObservationServer {

    private Logger log = LoggerFactory.getLogger(ObservationProxyServer.class);

    private ObservationClientService observationClientService;

    ObservationProxyServer(ObservationClientService observationClientService) {
        log.info("Observation proxy server initialised.");
        this.observationClientService = observationClientService;
    }

    @Override
    @ApiOperation(value = "List all observations matching the constraint", produces = "application/json")
    public ResponseEntity<StreamingResponseBody> query(Query query) {
        log.info("Query for user {}. Query: {}", CurrentUser.getLogin(), query);
        StreamingResponseBody stream = output -> observationClientService.fetchObservations(query,
            input -> {
                try {
                    Streams.copy(input, output, false);
                } catch (IOException e) {
                    e.printStackTrace();
                    throw new ServiceNotAvailable("Error reading observations", e);
                }
            });
        return ResponseEntity.ok()
            .contentType(MediaType.APPLICATION_JSON)
            .body(stream);
    }

}
