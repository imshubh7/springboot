package com.sdelab.sdelab.advice;

import com.sdelab.sdelab.constants.ErrorConstant;
import com.sdelab.sdelab.dto.ErrorResponse;
import com.sdelab.sdelab.exception.WeatherRuntimeException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.UUID;

@RestControllerAdvice
public class WeatherRuntimeExceptionHandler {

    private final String CORRELATION_ID = "correlationId";

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(WeatherRuntimeException.class)
    public ErrorResponse handleException(WeatherRuntimeException e, HttpServletRequest request){

        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setStatus(ErrorConstant.STATUS_400);
        errorResponse.setError(ErrorConstant.ERROR_BAD_REQUEST);
        errorResponse.setError_code(ErrorConstant.ERROR_CODE_4000);
        errorResponse.setMessage(e.getMessage());
        errorResponse.setError_type(ErrorConstant.ERROR_TYPE_VALIDATION);
        errorResponse.setTimestamp(LocalDateTime.now().toString());
        errorResponse.setRequest_id((String) request.getAttribute(CORRELATION_ID));

        return errorResponse;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ConstraintViolationException.class)
    public ErrorResponse handleValidationException(ConstraintViolationException e, HttpServletRequest request) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setStatus(ErrorConstant.STATUS_400);
        errorResponse.setError(ErrorConstant.ERROR_BAD_REQUEST);
        errorResponse.setError_code(ErrorConstant.ERROR_CODE_4000);
        errorResponse.setMessage("Invalid :Latitude");
        errorResponse.setError_type(ErrorConstant.ERROR_TYPE_VALIDATION);
        errorResponse.setTimestamp(LocalDateTime.now().toString());
        errorResponse.setRequest_id((String) request.getAttribute(CORRELATION_ID));

        return errorResponse;
    }
}
