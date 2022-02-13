package com.arkandas.vulkanite.errors;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
public class InternalErrorException extends Exception {

    private static final long serialVersionUID = 1L;

    public InternalErrorException(String message) {
        super(message);
    }
}
