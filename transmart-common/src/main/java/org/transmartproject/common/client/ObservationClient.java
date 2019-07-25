package org.transmartproject.common.client;

import org.springframework.core.io.InputStreamResource;
import org.transmartproject.common.dto.Query;

import javax.validation.Valid;
import java.io.InputStream;
import java.util.function.Consumer;

/**
 * Client for observations.
 * Specifies the endpoint URLs and data types for
 * observation clients and servers.
 * The response type is an {@link InputStreamResource}.
 */
public interface ObservationClient {

    /**
     * POST  /v2/observations : query observations.
     *
     * @param query the object that contains the constraint.
     * @param reader the input stream reader
     */
    void query(@Valid Query query, Consumer<InputStream> reader);

}
