package com.wust.advanced.web.utils.exceptions;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.zalando.problem.Problem;
import org.zalando.problem.Status;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class RestExceptionHandler {
    @ExceptionHandler(ItemNotFoundException.class)
    public ResponseEntity<Problem> handleNotFoundException(ItemNotFoundException ex) {
        Problem problem = buildProblem(Status.NOT_FOUND, "Not Found", ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(problem);
    }

    @ExceptionHandler(ItemExistsException.class)
    public ResponseEntity<Problem> handleObjectAlreadyExists(ItemExistsException ex) {
        Problem problem = buildProblem(Status.CONFLICT, "Already Exist", ex.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(problem);
    }

    @ExceptionHandler(AuthenticationCredentialsNotFoundException.class)
    public ResponseEntity<Problem> handleAuthenticationCredentialsNotFoundException(AuthenticationCredentialsNotFoundException ex) {
        Problem problem = buildProblem(Status.CONFLICT, "Authentication Exception", ex.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(problem);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Problem> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        FieldError fieldError = e.getBindingResult().getFieldError();
        String errorMessage = fieldError != null ? fieldError.getDefaultMessage() : "Invalid request";
        Problem problem = buildProblem(Status.BAD_REQUEST, "Invalid request", errorMessage);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(problem);
    }

    private Problem buildProblem(Status status, String title, String detail) {
        return Problem.builder()
                      .withStatus(status)
                      .withTitle(title)
                      .withDetail(detail)
                      .build();
    }
}