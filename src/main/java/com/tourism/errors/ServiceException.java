package com.tourism.errors;


public class ServiceException extends RuntimeException{

    private static final long serialVersionUID = -5089214307306283354L;
    private String message = "Internal Service Error";

    @Override
    public String getMessage() {
        return message;
    }

    public ServiceException(String message) {
        this.message = message;
    }

}
