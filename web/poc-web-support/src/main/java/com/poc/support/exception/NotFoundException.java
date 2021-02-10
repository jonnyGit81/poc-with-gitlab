package com.poc.support.exception;

public class NotFoundException extends RuntimeException {
    private static final long serialVersionUID = -7832865141898571447L;

    public NotFoundException() {}

    public NotFoundException(String message) {
        super(message);
    }

    public NotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public NotFoundException(Throwable cause) {
        super(cause);
    }
}
