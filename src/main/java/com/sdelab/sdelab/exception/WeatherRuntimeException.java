package com.sdelab.sdelab.exception;

public class WeatherRuntimeException extends RuntimeException{

    private int status;

    public WeatherRuntimeException(int status){
        super();
        this.status = status;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
