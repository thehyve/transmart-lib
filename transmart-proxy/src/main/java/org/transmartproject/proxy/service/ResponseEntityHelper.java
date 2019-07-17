package org.transmartproject.proxy.service;

import org.springframework.http.ResponseEntity;
import org.transmartproject.common.exception.AccessDenied;
import org.transmartproject.common.exception.ResourceNotFound;
import org.transmartproject.common.exception.ServiceNotAvailable;
import org.transmartproject.common.exception.Unauthorised;

public class ResponseEntityHelper {

    public static <T>T unwrap(ResponseEntity<T> response) {
        switch(response.getStatusCode()) {
            case OK: return response.getBody();
            case CREATED: return response.getBody();
            case UNAUTHORIZED: throw new Unauthorised("Client not authorised");
            case FORBIDDEN: throw new AccessDenied("Access to entity not allowed");
            case NOT_FOUND: throw new ResourceNotFound("Entity not found");
            default: throw new ServiceNotAvailable("Could not retrieve entity");
        }
    }

}
