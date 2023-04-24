package com.lucas.restspringboot.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class BadRequestBusinessException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public BadRequestBusinessException(String ex) {
        super(ex);
    }
}
