package org.companiesOnMarket.error;

import io.quarkus.logging.Log;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class GlobalExceptionMapper implements ExceptionMapper<Exception> {
    private final static int DEFAULT_STATUS_CODE = Response.Status.INTERNAL_SERVER_ERROR.getStatusCode();

    @Override
    public Response toResponse(Exception e) {
        if (e instanceof ApiException apiEx)
        {
            return Response.status(apiEx.getStatusCode())
                    .entity(new ErrorResponse(apiEx.getMessage(), apiEx.getStatusCode()))
                    .build();
        }

        Log.error(e);
        return Response.status(DEFAULT_STATUS_CODE)
                .entity(new ErrorResponse("Unexpected error occurred", DEFAULT_STATUS_CODE))
                .build();
    }
}
