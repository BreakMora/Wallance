package com.wallance.api.exception;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import lombok.extern.slf4j.Slf4j;

/**
 * {@GlobalHandlerException} esta clase hereda metodos de
 * {@ResponseEntityExceptionHandler}
 * Esta clase atiende las excepciones de todos los controladores.
 */

@RestControllerAdvice
@Slf4j
public class GlobalHandlerException extends ResponseEntityExceptionHandler {

    // 404 - Recurso inexistente
    @ExceptionHandler(ResourceNotFoundException.class)
    public ProblemDetail HandleNotFound(ResourceNotFoundException ex) {
        ProblemDetail problem = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(404), ex.getMessage());
        problem.setTitle("Resource Not Found");
        return problem;
    }

    // 400, 409, 422, ... - Reglas de negocio con status propio
    @ExceptionHandler(BusinessException.class)
    public ProblemDetail HandleBussine(BusinessException ex) {
        ProblemDetail problem = ProblemDetail.forStatusAndDetail(ex.getStatus(), ex.getMessage());
        problem.setTitle(ex.getStatus().getReasonPhrase());
        return problem;
    }

    // 400 - @Valid error: devuelve que campo fallo y el por qué
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
            HttpHeaders headers, HttpStatusCode statusCode, WebRequest request) {
        Map<String, String> error = new LinkedHashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(e -> error.putIfAbsent(e.getField(), e.getDefaultMessage()));

        ProblemDetail problem = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, "Invalid Data");
        problem.setTitle("Validation Error");
        problem.setProperty("errors", error);
        return ResponseEntity.badRequest().body(problem);
    }

    // 500 - Recurso inexistente
    @ExceptionHandler(Exception.class)
    public ProblemDetail HandleUnexpected(Exception e) {
        log.error("Uncontrolled Error", e);
        ProblemDetail problem = ProblemDetail.forStatusAndDetail(HttpStatus.INTERNAL_SERVER_ERROR, "Unexpected Error");
        problem.setTitle("Internal Error");
        return problem;
    }
}
