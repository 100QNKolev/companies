package org.companiesOnMarket.error;

public abstract class ApiException extends RuntimeException
{
    private final int statusCode;

    public ApiException(String message, int statusCode)
    {
        super(message);
        this.statusCode = statusCode;
    }

    public int getStatusCode()
    {
        return statusCode;
    }
}