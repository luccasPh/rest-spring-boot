package com.lucas.restspringboot.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class NotFoundBusinessException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public NotFoundBusinessException(String ex) {
        super(ex);
    }
}
