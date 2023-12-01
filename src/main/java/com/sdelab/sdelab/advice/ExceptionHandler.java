package com.sdelab.sdelab.advice;

import com.sdelab.sdelab.dto.ErrorResponse;
import com.sdelab.sdelab.exception.NotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.net.UnknownHostException;
import java.util.concurrent.TimeoutException;

@RestControllerAdvice
public class ExceptionHandler {

    private final String CORRELATION_ID = "correlationId";
    private final String ERROR_MESSAGE = "Request with Correlation ID: {} failed with error: {}";

    private final Logger log = LoggerFactory.getLogger(ExceptionHandler.class);

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @org.springframework.web.bind.annotation.ExceptionHandler(NotFoundException.class)
    public ErrorResponse handleNotFoundException(NotFoundException e, HttpServletRequest request){

        String correlationId = (String) request.getAttribute(CORRELATION_ID);
        log.error(ERROR_MESSAGE, correlationId, e.getMessage());

        return new ErrorResponse(e.getStatus(), correlationId);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @org.springframework.web.bind.annotation.ExceptionHandler(ConstraintViolationException.class)
    public ErrorResponse handleValidationException(ConstraintViolationException e, HttpServletRequest request) {

        String correlationId = (String) request.getAttribute(CORRELATION_ID);
        log.error(ERROR_MESSAGE, correlationId, e.getMessage());

        return new ErrorResponse(HttpStatus.BAD_REQUEST.value(), correlationId);
    }

    @ResponseStatus(HttpStatus.BAD_GATEWAY)
    @org.springframework.web.bind.annotation.ExceptionHandler(UnknownHostException.class)
    public ErrorResponse handleUnknownHostException(UnknownHostException e, HttpServletRequest request) {

        String correlationId = (String) request.getAttribute(CORRELATION_ID);
        log.error(ERROR_MESSAGE, correlationId, e.getMessage());

        return new ErrorResponse(HttpStatus.BAD_GATEWAY.value(), correlationId);
    }

    @ResponseStatus(HttpStatus.GATEWAY_TIMEOUT)
    @org.springframework.web.bind.annotation.ExceptionHandler(TimeoutException.class)
    public ErrorResponse handleTimeOutException(TimeoutException e, HttpServletRequest request) {

        String correlationId = (String) request.getAttribute(CORRELATION_ID);
        log.error(ERROR_MESSAGE, correlationId, e.getMessage());

        return new ErrorResponse(HttpStatus.GATEWAY_TIMEOUT.value(), correlationId);
    }
}
