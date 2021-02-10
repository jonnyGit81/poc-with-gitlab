package com.poc.support.exception;

public class InvalidInputException extends RuntimeException {
    private static final long serialVersionUID = 5425268756574300543L;

    public InvalidInputException() {}

    public InvalidInputException(String message) {
        super(message);
    }

    public InvalidInputException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidInputException(Throwable cause) {
        super(cause);
    }
}
