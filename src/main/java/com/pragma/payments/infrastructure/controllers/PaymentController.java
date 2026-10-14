package com.pragma.payments.infrastructure.controllers;

import com.pragma.payments.domain.model.PaymentRequest;
import com.pragma.payments.domain.ports.in.ProcessPaymentUseCase;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/payments")
public class PaymentController {

    private static final Logger log = LoggerFactory.getLogger(PaymentController.class);
    private final ProcessPaymentUseCase processPaymentUseCase;

    public PaymentController(ProcessPaymentUseCase processPaymentUseCase) {
        this.processPaymentUseCase = processPaymentUseCase;
    }

    @PostMapping("/mobile")
    public Mono<ResponseEntity<Map<String, Object>>> processMobilePayment(
            @Valid @RequestBody PaymentRequestDto request) {
        log.info("Recibida solicitud de pago desde canal móvil, idempotencyKey: {}", request.idempotencyKey());
        return processPayment(request.idempotencyKey(), request);
    }

    @PostMapping("/web")
    public Mono<ResponseEntity<Map<String, Object>>> processWebPayment(
            @Valid @RequestBody PaymentRequestDto request) {
        log.info("Recibida solicitud de pago desde canal web, idempotencyKey: {}", request.idempotencyKey());
        return processPayment(request.idempotencyKey(), request);
    }

    @PostMapping("/partner")
    public Mono<ResponseEntity<Map<String, Object>>> processPartnerPayment(
            @Valid @RequestBody PaymentRequestDto request) {
        log.info("Recibida solicitud de pago desde canal socio, idempotencyKey: {}", request.idempotencyKey());
        return processPayment(request.idempotencyKey(), request);
    }

    @GetMapping("/status/{idempotencyKey}")
    public Mono<ResponseEntity<Map<String, Object>>> getPaymentStatus(
            @PathVariable UUID idempotencyKey) {
        log.info("Consultando estado del pago con idempotencyKey: {}", idempotencyKey);
        return processPaymentUseCase.checkPaymentStatus(idempotencyKey)
                .map(status -> ResponseEntity.ok(Map.of(
                        "idempotencyKey", idempotencyKey.toString(),
                        "status", status.name(),
                        "description", status.getDescription()
                )))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    private Mono<ResponseEntity<Map<String, Object>>> processPayment(UUID idempotencyKey, PaymentRequestDto dto) {
        PaymentRequest paymentRequest = new PaymentRequest(
                idempotencyKey,
                dto.amount(),
                dto.currency(),
                dto.payerId(),
                dto.payeeId(),
                dto.description()
        );
        paymentRequest.validate();

        return processPaymentUseCase.processPayment(paymentRequest)
                .map(status -> ResponseEntity.status(HttpStatus.ACCEPTED).body(Map.of(
                        "idempotencyKey", idempotencyKey.toString(),
                        "status", status.name(),
                        "description", status.getDescription(),
                        "message", "Pago recibido y en procesamiento"
                )));
    }

    public record PaymentRequestDto(
            UUID idempotencyKey,
            java.math.BigDecimal amount,
            String currency,
            String payerId,
            String payeeId,
            String description
    ) {}
}