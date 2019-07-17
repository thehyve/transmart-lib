package org.transmartproject.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.UNAUTHORIZED)
public class Unauthorised extends RuntimeException {

    public Unauthorised(String s) {
        super(s);
    }

    public Unauthorised(String s, Throwable throwable) {
        super(s, throwable);
    }

}
