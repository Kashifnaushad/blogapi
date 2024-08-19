package com.myblogapp.exception;

import org.springframework.http.HttpStatus;

public class BlogApiException extends RuntimeException {
    private HttpStatus status;
    private String message;

    public BlogApiException(HttpStatus status, String message) {
        super(message); // super keyword will call constructor of parent class.
        this.status = status;
        this.message = message;
    }

    public HttpStatus getStatus() {
        return status;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
