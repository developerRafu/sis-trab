package com.rafu.sistrab.errors;

public class NotFoundException extends RuntimeException {
    public NotFoundException(final String msg) {
        super(msg);
    }
}
