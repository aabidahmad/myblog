package com.myblog7.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ResourceNotFound1 extends RuntimeException{
    public ResourceNotFound1(String msg) {
        super(msg);
    }
}
