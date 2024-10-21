package com.shrtly.url.shortener.utils;

public class StandardApiResponse<T> {
    private boolean success;
    private String message;
    private T data;

    // Constructors
    public StandardApiResponse() {}

    public StandardApiResponse(boolean success, String message, T data) {
        this.success = success;
        this.message = message;
        this.data = data;
    }

    // Getters and setters
    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
