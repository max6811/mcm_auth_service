package com.maxcmart.mcm_auth_service.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String resource, String name, String value) {
        super(String.format("%s not found with %s : '%s'", resource, name, value));
    }
}
