package com.firstaware.client.controller;

import com.firstaware.client.exception.HttpStatusResponseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Locale;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@RestControllerAdvice
public class ClientApiControllerAdvice {


    private static final Logger LOG = LoggerFactory.getLogger(ClientApiControllerAdvice.class);

    @Autowired
    private MessageSource messageSource;


    @ExceptionHandler(value = HttpStatusResponseException.class)
    public void handleHttpStatusException(HttpStatusResponseException ex, HttpServletResponse response, Locale locale)
            throws IOException {
        String msg = messageSource.getMessage(ex.getErrorCode(), ex.getErrorArgs(), ex.getLocalizedMessage(), locale);
        LOG.info("Http Status Exception occurred {}", msg);
        response.sendError(ex.getRawStatusCode(), msg);
    }

    @ExceptionHandler(value = RuntimeException.class)
    public void handleRuntimeException(RuntimeException ex, HttpServletResponse response, Locale locale)
            throws IOException {
        LOG.info("Runtime Exception occurred {}", ex.getMessage());
        response.sendError(INTERNAL_SERVER_ERROR.value(), ex.getMessage());
    }

}
