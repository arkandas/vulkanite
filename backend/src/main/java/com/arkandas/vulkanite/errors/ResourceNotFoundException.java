package com.arkandas.vulkanite.errors;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends Exception {

    private static long serialVersionUID = 1L;

    public ResourceNotFoundException(String message) {
        super(message);
    }
}
