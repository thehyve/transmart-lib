package org.transmartproject.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ResourceNotFound extends RuntimeException {

    public ResourceNotFound(String s) {
        super(s);
    }

    public ResourceNotFound(String s, Throwable throwable) {
        super(s, throwable);
    }

}
