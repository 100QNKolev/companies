package org.companiesOnMarket.error;

import java.util.List;

public class ErrorResponse {
    public String message;
    public int code;
    public List<String> details;

    public ErrorResponse(String message, int code)
    {
        this.message = message;
        this.code = code;
    }

    public ErrorResponse(String message, int code, List<String> details) {
        this.message = message;
        this.code = code;
        this.details = details;
    }
}
