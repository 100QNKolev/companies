package org.companiesOnMarket.error;

public class ErrorResponse {
    public String message;
    public int code;

    public ErrorResponse(String message, int code)
    {
        this.message = message;
        this.code = code;
    }
}
