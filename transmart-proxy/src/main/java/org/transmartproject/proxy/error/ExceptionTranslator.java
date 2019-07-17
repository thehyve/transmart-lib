package org.transmartproject.proxy.error;

import feign.FeignException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.ResponseEntity.BodyBuilder;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.transmartproject.common.dto.error.ErrorRepresentation;
import org.transmartproject.common.exception.AccessDenied;
import org.transmartproject.common.exception.ResourceNotFound;
import org.transmartproject.common.exception.ServiceNotAvailable;
import org.transmartproject.common.exception.Unauthorised;

import java.util.List;

/**
 * Controller advice to translate the server side exceptions to client-friendly json structures.
 */
@ControllerAdvice
public class ExceptionTranslator {

    private final Logger log = LoggerFactory.getLogger(ExceptionTranslator.class);

    @ExceptionHandler(Unauthorised.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ResponseBody
    public ErrorRepresentation processUnauthorised(Unauthorised e) {
        return new ErrorRepresentation(e.getMessage());
    }

    @ExceptionHandler(FeignException.Unauthorized.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ResponseBody
    public ErrorRepresentation processUnauthorised(FeignException.Unauthorized e) {
        return new ErrorRepresentation(e.getMessage());
    }

    @ExceptionHandler(AccessDenied.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ResponseBody
    public ErrorRepresentation processAccessDenied(AccessDenied e) {
        return new ErrorRepresentation(e.getMessage());
    }

    @ExceptionHandler(ResourceNotFound.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public ErrorRepresentation processResourceNotFound(ResourceNotFound e) {
        return new ErrorRepresentation(e.getMessage());
    }

    @ExceptionHandler(ServiceNotAvailable.class)
    @ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
    @ResponseBody
    public ErrorRepresentation processServiceNotAvailable(ServiceNotAvailable e) {
        return new ErrorRepresentation(e.getMessage());
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    public ErrorRepresentation processMethodNotSupportedException(HttpRequestMethodNotSupportedException exception) {
        return new ErrorRepresentation(exception.getMessage());
    }

    private ErrorRepresentation processFieldErrors(String message, List<FieldError> fieldErrors) {
        ErrorRepresentation dto = new ErrorRepresentation(message);
        for (FieldError fieldError: fieldErrors) {
            dto.add(fieldError.getObjectName(), fieldError.getField(), fieldError.getCode());
        }
        return dto;
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorRepresentation processValidationError(MethodArgumentNotValidException ex) {
        BindingResult result = ex.getBindingResult();
        List<FieldError> fieldErrors = result.getFieldErrors();
        return processFieldErrors(ex.getMessage(), fieldErrors);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorRepresentation> processRuntimeException(Exception ex) {
        BodyBuilder builder;
        ErrorRepresentation errorVM;
        ResponseStatus responseStatus = AnnotationUtils.findAnnotation(ex.getClass(), ResponseStatus.class);
        log.error("[Internal server error] {} - {}", ex.getMessage(), ex.getClass().toString());
        if (ex instanceof FeignException) {
            log.error("[Internal server error] Content: {}", ((FeignException)ex).contentUTF8());
        }
        log.error("[Internal server error] Stack trace", ex);
        if (responseStatus != null) {
            builder = ResponseEntity.status(responseStatus.value());
            errorVM = new ErrorRepresentation(responseStatus.reason());
        } else {
            builder = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR);
            errorVM = new ErrorRepresentation("Internal server error");
        }
        return builder.body(errorVM);
    }

}
