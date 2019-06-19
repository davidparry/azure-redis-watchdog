package com.firstaware.client.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpStatusCodeException;

import java.util.Arrays;

public class HttpStatusResponseException extends HttpStatusCodeException {


    private String errorCode;
    private Object[] errorArgs = new Object[0];

    public HttpStatusResponseException(HttpStatus statusCode) {
        super(statusCode);
    }

    public HttpStatusResponseException(HttpStatus statusCode, String statusText) {
        super(statusCode, statusText);
    }

    public String getErrorCode() {
        return errorCode;
    }

    public HttpStatusResponseException setErrorCode(String errorCode) {
        this.errorCode = errorCode;
        return this;
    }

    public Object[] getErrorArgs() {
        return Arrays.copyOf(errorArgs, errorArgs.length);
    }

    public HttpStatusResponseException setErrorArgs(Object... errorArgs) {
        this.errorArgs = errorArgs == null ? new Object[0] : Arrays.copyOf(errorArgs, errorArgs.length);
        return this;
    }

}
