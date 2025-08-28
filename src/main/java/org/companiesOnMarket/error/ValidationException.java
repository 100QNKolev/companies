package org.companiesOnMarket.error;

import jakarta.ws.rs.core.Response;

public class ValidationException extends ApiException {

    public ValidationException(String message) {
        super(message, Response.Status.BAD_REQUEST.getStatusCode());
    }
}

