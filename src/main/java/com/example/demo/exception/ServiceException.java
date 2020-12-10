package com.example.demo.exception;

import org.springframework.http.HttpStatus;

public class ServiceException extends Exception {
    private HttpStatus status;
    private String object;
    private String message;

    public ServiceException(HttpStatus status, String objeto, String message) {
        this.status = status;
        this.object = objeto;
        this.message = message;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }

    public String getObject() {
        return object;
    }

    public void setObject(String object) {
        this.object = object;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}