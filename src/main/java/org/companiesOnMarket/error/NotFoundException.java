package org.companiesOnMarket.error;

import jakarta.ws.rs.core.Response;

public class NotFoundException extends ApiException {
    public NotFoundException(String message) {
        super(message, Response.Status.NOT_FOUND.getStatusCode());
    }
}
