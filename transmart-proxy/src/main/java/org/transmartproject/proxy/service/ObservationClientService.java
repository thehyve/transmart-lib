package org.transmartproject.proxy.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.transmartproject.common.client.ObservationClient;
import org.transmartproject.common.dto.Hypercube;
import org.transmartproject.common.dto.Query;
import org.transmartproject.common.constraint.Constraint;

@Service
public class ObservationClientService {

    private ObservationClient observationClient;

    public ObservationClientService(ObservationClient observationClient) {
        this.observationClient = observationClient;
    }

    public Hypercube fetchObservations(Query query) {
        ResponseEntity<Hypercube> response = observationClient.query(query);
        return ResponseEntityHelper.unwrap(response);
    }

}
