package org.transmartproject.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.FORBIDDEN)
public class AccessDenied extends RuntimeException {

    public AccessDenied(String s) {
        super(s);
    }

    public AccessDenied(String s, Throwable throwable) {
        super(s, throwable);
    }

}
