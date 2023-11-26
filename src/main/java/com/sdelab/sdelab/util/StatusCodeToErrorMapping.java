package com.sdelab.sdelab.util;

import com.sdelab.sdelab.exception.NotFoundException;
import com.sdelab.sdelab.exception.WeatherRuntimeException;

public class StatusCodeToErrorMapping {

    public static String getError(int statusCode){
        switch (statusCode) {
            case 400:
                return "Bad Request: The request could not be understood or was missing required parameters.";
            case 401:
                return "Unauthorized: Authentication is required and has failed or has not been provided.";
            case 403:
                return "Forbidden: The server understood the request but refuses to authorize it.";
            case 404:
                return "Not Found: The requested resource could not be found on the server.";
            case 500:
                return "Internal Server Error: The server encountered an unexpected condition.";
            case 502:
                return "Network Error: Error Connecting Host";
            case 504:
                return "Gateway Timeout: The request to the external service timed out.";
            default:
                return "Unknown Error: An unexpected error occurred with status code " + statusCode + ".";
        }
    }

    public static String getErrorCode(int statusCode){
        switch (statusCode) {
            case 400:
                return "4000";
            case 401:
                return "4001";
            case 404:
                return "4002";
            case 504:
                return "4004";
            default:
                return "5000";
        }
    }

    public static String getMessage(int statusCode){
        switch (statusCode) {
            case 400:
                return "Bad Request: The request could not be understood or was missing required parameters.";
            case 401:
                return "Unauthorized: Authentication is required and has failed or has not been provided.";
            case 403:
                return "Forbidden: The server understood the request but refuses to authorize it.";
            case 404:
                return "Not Found: Please check if the path is correct";
            case 500:
                return "Internal Server Error: The server encountered an unexpected condition.";
            case 502:
                return "Network Error: Encountered error while connecting to Host";
            case 504:
                return "Gateway Timeout: The request to the external service timed out. Please try again later";
            default:
                return "Unknown Error: An unexpected error occurred with status code " + statusCode + ".";
        }
    }
    public static String getErrorType(int statusCode) {
        switch (statusCode) {
            case 400:
                return "Validation";
            case 401:
                return "Unauthorized";
            case 403:
                return "Forbidden";
            case 404:
                return "Client Error";
            case 500:
                return "Internal Server Error";
            case 502:
                return "Network Error";
            case 504:
                return "Gateway Timeout";
            default:
                return "Unknown Error";
        }
    }
    public static Exception getException(int statusCode){
        switch (statusCode) {
            case 404:
                return new NotFoundException(statusCode, "Resource Not Found");
            default:
                return new WeatherRuntimeException(statusCode);
        }
    }



}
