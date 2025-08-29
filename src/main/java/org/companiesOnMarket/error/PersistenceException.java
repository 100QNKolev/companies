package org.companiesOnMarket.error;

import jakarta.ws.rs.core.Response;

public class PersistenceException extends ApiException {
    public PersistenceException(String message) {
        super(message, Response.Status.BAD_REQUEST.getStatusCode());
    }
}
