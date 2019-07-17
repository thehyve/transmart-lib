package org.transmartproject.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.SERVICE_UNAVAILABLE)
public class ServiceNotAvailable extends RuntimeException {

    public ServiceNotAvailable(String s) {
        super(s);
    }

    public ServiceNotAvailable(String s, Throwable throwable) {
        super(s, throwable);
    }

}
