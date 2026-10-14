package com.pragma.payments.infrastructure.config;

import com.pragma.payments.domain.exception.BackpressureException;
import com.pragma.payments.domain.exception.DuplicatePaymentException;
import com.pragma.payments.domain.exception.PaymentProcessingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import reactor.core.publisher.Mono;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(DuplicatePaymentException.class)
    public Mono<ResponseEntity<Map<String, Object>>> handleDuplicatePaymentException(DuplicatePaymentException ex) {
        log.warn("Pago duplicado detectado: {}", ex.getMessage());
        Map<String, Object> body = buildErrorResponse(
                HttpStatus.CONFLICT.value(),
                "CONFLICT",
                ex.getMessage()
        );
        return Mono.just(ResponseEntity.status(HttpStatus.CONFLICT).body(body));
    }

    @ExceptionHandler(PaymentProcessingException.class)
    public Mono<ResponseEntity<Map<String, Object>>> handlePaymentProcessingException(PaymentProcessingException ex) {
        log.error("Error procesando pago: {}", ex.getMessage(), ex);
        Map<String, Object> body = buildErrorResponse(
                HttpStatus.UNPROCESSABLE_ENTITY.value(),
                "PAYMENT_PROCESSING_ERROR",
                ex.getMessage()
        );
        return Mono.just(ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(body));
    }

    @ExceptionHandler(BackpressureException.class)
    public Mono<ResponseEntity<Map<String, Object>>> handleBackpressureException(BackpressureException ex) {
        log.warn("Excepción de backpressure: {}", ex.getMessage());
        Map<String, Object> body = buildErrorResponse(
                HttpStatus.SERVICE_UNAVAILABLE.value(),
                "SERVICE_UNAVAILABLE",
                ex.getMessage()
        );
        return Mono.just(ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(body));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Mono<ResponseEntity<Map<String, Object>>> handleValidationException(MethodArgumentNotValidException ex) {
        log.warn("Error de validación: {}", ex.getMessage());
        String errors = ex.getBindingResult().getFieldErrors().stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .collect(Collectors.joining(", "));
        Map<String, Object> body = buildErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                "VALIDATION_ERROR",
                errors
        );
        return Mono.just(ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body));
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public Mono<ResponseEntity<Map<String, Object>>> handleIllegalArgumentException(IllegalArgumentException ex) {
        log.warn("Argumento ilegal: {}", ex.getMessage());
        Map<String, Object> body = buildErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                "BAD_REQUEST",
                ex.getMessage()
        );
        return Mono.just(ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body));
    }

    @ExceptionHandler(Exception.class)
    public Mono<ResponseEntity<Map<String, Object>>> handleGenericException(Exception ex) {
        log.error("Error inesperado: {}", ex.getMessage(), ex);
        Map<String, Object> body = buildErrorResponse(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "INTERNAL_SERVER_ERROR",
                "Ocurrió un error inesperado al procesar la solicitud"
        );
        return Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(body));
    }

    private Map<String, Object> buildErrorResponse(int status, String error, String message) {
        Map<String, Object> body = new HashMap<>();
        body.put("timestamp", Instant.now().toString());
        body.put("status", status);
        body.put("error", error);
        body.put("message", message);
        return body;
    }
}