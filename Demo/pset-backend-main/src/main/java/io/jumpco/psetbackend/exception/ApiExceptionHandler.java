package io.jumpco.psetbackend.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import javax.persistence.EntityNotFoundException;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@ControllerAdvice
public class ApiExceptionHandler {
    public ResponseEntity<Object> handleExceptions(ApiException apiException){
        // 1. Create payload containing exception
        HttpStatus badRequest = HttpStatus.BAD_REQUEST;
        ApiException exception = new ApiException(
                apiException.getMessage(),
                apiException,
                badRequest,
                ZonedDateTime.now()
        );
        // 2. Return response entity
        return new ResponseEntity<>(apiException, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({ EntityNotFoundException.class })
    public ResponseEntity<Object> handleEntityException(Exception ex, WebRequest webRequest){
        return new ResponseEntity<Object>("Access denied message here", new HttpHeaders(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({ConstraintViolationException.class })
    public ResponseEntity<Object> handleValidationException(ConstraintViolationException constraintViolationException){
        Set<ConstraintViolation<?>> constraintViolations = constraintViolationException.getConstraintViolations();
        Set<ViolationInformation> messages = new HashSet<>(constraintViolations.size());
        messages.addAll(constraintViolations.stream().map(constraintViolation -> new ViolationInformation(
                constraintViolation.getInvalidValue().toString(),
                constraintViolation.getMessage(),
                constraintViolation.getPropertyPath().toString())).collect(Collectors.toList()));
        ValidationErrorResponse validationErrorResponse = new ValidationErrorResponse(
                messages,
                HttpStatus.BAD_REQUEST,
                ZonedDateTime.now());
        return new ResponseEntity<>(validationErrorResponse, HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler({ IllegalArgumentException.class })
    public ResponseEntity<Object> handleIllegalArgumentException(IllegalArgumentException illegalArgumentException){
        return new ResponseEntity<>(illegalArgumentException.getMessage(), HttpStatus.BAD_REQUEST);
    }
}
