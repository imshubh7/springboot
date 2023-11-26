package com.sdelab.sdelab.exception;

public class NotFoundException extends Exception{

    private int status;

    public NotFoundException(int status, String message) {
        super(message);
        this.status = status;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
