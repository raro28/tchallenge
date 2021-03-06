package mx.ekthor.challenge.rest;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import mx.ekthor.challenge.Main;
import mx.ekthor.challenge.rest.models.responses.Error;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.HandlerMethod;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolationException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.TimeZone;
import java.util.stream.Collectors;

@RestControllerAdvice
public class DefaultControllerAdvice {

    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultControllerAdvice.class);

    @ExceptionHandler({JsonParseException.class, ConstraintViolationException.class, UnrecognizedPropertyException.class})
    @ApiResponse(responseCode = "400", description = "Bad Request", content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = Error.class))})
    public ResponseEntity<Error> badRequest(Exception ex, HandlerMethod handlerMethod, HttpServletRequest request) {
        return getDefaultResponse(ex, HttpStatus.BAD_REQUEST, handlerMethod, request);
    }

    @ExceptionHandler(Exception.class)
    @ApiResponse(responseCode = "500", description = "Internal Server Error", content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = Error.class))})
    public ResponseEntity<Error> internalServerError(Exception ex, HandlerMethod handlerMethod, HttpServletRequest request) {
        return getDefaultResponse(ex, HttpStatus.INTERNAL_SERVER_ERROR, handlerMethod, request);
    }

    private ResponseEntity<Error> getDefaultResponse(Exception ex, HttpStatus status, HandlerMethod handlerMethod, HttpServletRequest request) {
        Class<?> controllerName = handlerMethod.getMethod().getDeclaringClass();

        DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm'Z'");
        df.setTimeZone(TimeZone.getTimeZone("UTC"));

        Error error = Error
                .builder()
                .timestamp(df.format(new Date()))
                .status(status.value())
                .error(status.getReasonPhrase())
                .message(status.getReasonPhrase())
                .path(request.getRequestURL().toString())
                .build();

        LOGGER.info(String.format("%s: %s", controllerName.getName(), error));

        filterStackTrace(ex);

        LOGGER.info(ex.getMessage(), ex);

        return ResponseEntity.status(status).body(error);
    }

    private void filterStackTrace(Throwable ex) {
        ex.setStackTrace(Arrays
                .stream(ex.getStackTrace())
                .filter(se -> se.getClassName().startsWith(Main.class.getPackage().getName()))
                .collect(Collectors.toList())
                .toArray(new StackTraceElement[0]));

        if (ex.getCause() != null) {
            filterStackTrace(ex.getCause());
        }
    }
}