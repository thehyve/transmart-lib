package org.transmartproject.proxy.service;

import org.springframework.stereotype.Service;
import org.transmartproject.common.client.ObservationClient;
import org.transmartproject.common.dto.Query;

import java.io.InputStream;
import java.util.function.Consumer;

@Service
public class ObservationClientService {

    private ObservationClient observationClient;

    public ObservationClientService(ObservationClient observationClient) {
        this.observationClient = observationClient;
    }

    public void fetchObservations(Query query, Consumer<InputStream> reader) {
        observationClient.query(query, reader);
    }

}
