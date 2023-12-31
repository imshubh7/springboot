package com.sdelab.sdelab.dto;


import com.sdelab.sdelab.util.StatusCodeToErrorMapping;

import java.time.LocalDateTime;
import java.util.List;

public class ErrorResponse {

    private int status;
    private String error;
    private String error_code;
    private String message;
    private String error_type;
    private String timestamp;
    private String request_id;

    public ErrorResponse(int statusCode, String request_id) {
        this.request_id = request_id;
        status = statusCode;
        error = StatusCodeToErrorMapping.getError(statusCode);
        error_code = StatusCodeToErrorMapping.getErrorCode(statusCode);
        message = StatusCodeToErrorMapping.getMessage(statusCode);
        error_type = StatusCodeToErrorMapping.getErrorType(statusCode);
        timestamp = LocalDateTime.now().toString();
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getError_code() {
        return error_code;
    }

    public void setError_code(String error_code) {
        this.error_code = error_code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getError_type() {
        return error_type;
    }

    public void setError_type(String error_type) {
        this.error_type = error_type;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getRequest_id() {
        return request_id;
    }

    public void setRequest_id(String request_id) {
        this.request_id = request_id;
    }
}
