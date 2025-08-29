package org.companiesOnMarket.error;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import jakarta.validation.ConstraintViolationException;
import java.util.List;

@Provider
public class BeanExceptionMapper implements ExceptionMapper<ConstraintViolationException> {
    private final static int STATUS_CODE = Response.Status.BAD_REQUEST.getStatusCode();

    @Override
    public Response toResponse(ConstraintViolationException exception) {
        List<String> errors = exception.getConstraintViolations()
                .stream()
                // Don't like this but for now is ok
                .map(v -> {
                    String field = v.getPropertyPath().toString();
                    field = field.substring(field.lastIndexOf('.') + 1);
                    return field + " " + v.getMessage();
                })
                .toList();

        return Response.status(STATUS_CODE)
                .entity(new ErrorResponse("Validation failed", STATUS_CODE, errors))
                .build();
    }
}
