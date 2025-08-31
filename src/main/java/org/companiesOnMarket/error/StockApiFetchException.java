package org.companiesOnMarket.error;

import jakarta.ws.rs.core.Response;

public class StockApiFetchException extends ApiException {
    public StockApiFetchException(String message) {
        super(message, Response.Status.BAD_REQUEST.getStatusCode());
    }
}