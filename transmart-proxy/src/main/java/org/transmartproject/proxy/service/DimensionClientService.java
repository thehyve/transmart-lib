package org.transmartproject.proxy.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.transmartproject.common.client.DimensionClient;
import org.transmartproject.common.dto.Dimension;
import org.transmartproject.common.dto.DimensionList;
import org.transmartproject.common.exception.AccessDenied;
import org.transmartproject.common.exception.ResourceNotFound;
import org.transmartproject.common.exception.ServiceNotAvailable;
import org.transmartproject.common.exception.Unauthorised;

@Service
public class DimensionClientService {

    private DimensionClient dimensionClient;

    public DimensionClientService(DimensionClient dimensionClient) {
        this.dimensionClient = dimensionClient;
    }

    public DimensionList fetchDimensions() {
        ResponseEntity<DimensionList> response = dimensionClient.listDimensions();
        return ResponseEntityHelper.unwrap(response);
    }

    public Dimension fetchDimension(String name) {
        ResponseEntity<Dimension> response = dimensionClient.getDimension(name);
        return ResponseEntityHelper.unwrap(response);
    }

}
