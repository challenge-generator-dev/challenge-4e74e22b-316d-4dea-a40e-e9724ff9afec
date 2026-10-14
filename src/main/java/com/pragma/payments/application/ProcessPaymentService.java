package com.pragma.payments.application;

import com.pragma.payments.domain.exception.DuplicatePaymentException;
import com.pragma.payments.domain.exception.PaymentProcessingException;
import com.pragma.payments.domain.model.PaymentRequest;
import com.pragma.payments.domain.model.PaymentStatus;
import com.pragma.payments.domain.ports.in.ProcessPaymentUseCase;
import com.pragma.payments.domain.ports.out.IdempotencyRepositoryPort;
import com.pragma.payments.domain.ports.out.PaymentRepositoryPort;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProcessPaymentService implements ProcessPaymentUseCase {

    private final PaymentRepositoryPort paymentRepository;
    private final IdempotencyRepositoryPort idempotencyRepository;

    @Override
    @CircuitBreaker(name = "paymentCircuitBreaker", fallbackMethod = "fallbackProcessPayment")
    @Retry(name = "paymentRetry")
    public Mono<PaymentStatus> processPayment(PaymentRequest paymentRequest) {
        return Mono.fromCallable(() -> {
                    paymentRequest.validate();
                    return paymentRequest.idempotencyKey();
                })
                .subscribeOn(Schedulers.boundedElastic())
                .flatMap(this::checkAndStoreIdempotency)
                .flatMap(this::executePaymentProcessing)
                .doOnSuccess(status -> log.info("Pago procesado exitosamente con estado: {}", status))
                .doOnError(error -> log.error("Error al procesar pago: {}", error.getMessage()));
    }

    @Override
    public Mono<PaymentStatus> checkPaymentStatus(UUID idempotencyKey) {
        return Mono.fromCallable(() -> {
                    log.debug("Consultando estado del pago para clave de idempotencia: {}", idempotencyKey);
                    return idempotencyKey;
                })
                .subscribeOn(Schedulers.boundedElastic())
                .flatMap(idempotencyRepository::findByIdempotencyKey)
                .flatMap(idempotencyEntity -> {
                    if (idempotencyEntity == null) {
                        return Mono.error(new PaymentProcessingException(
                            "No se encontró registro para la clave de idempotencia: " + idempotencyKey));
                    }
                    return paymentRepository.findByIdempotencyKey(idempotencyKey)
                            .map(paymentEntity -> PaymentStatus.valueOf(paymentEntity.getStatus()))
                            .switchIfEmpty(Mono.just(PaymentStatus.PENDING));
                });
    }

    private Mono<PaymentStatus> checkAndStoreIdempotency(UUID idempotencyKey) {
        return idempotencyRepository.findByIdempotencyKey(idempotencyKey)
                .flatMap(existing -> {
                    log.warn("Clave de idempotencia duplicada detectada: {}", idempotencyKey);
                    return Mono.error(new DuplicatePaymentException(
                        "Ya existe un pago con esta clave de idempotencia: " + idempotencyKey));
                })
                .switchIfEmpty(Mono.defer(() -> {
                    log.info("Nueva clave de idempotencia, creando registro: {}", idempotencyKey);
                    return idempotencyRepository.save(idempotencyKey, PaymentStatus.PENDING.name(),
                        Instant.now().toString());
                }))
                .thenReturn(PaymentStatus.PENDING);
    }

    private Mono<PaymentStatus> executePaymentProcessing(UUID idempotencyKey) {
        return Mono.fromCallable(() -> {
                    log.info("Iniciando procesamiento del pago para idempotencyKey: {}", idempotencyKey);
                    BigDecimal amount = BigDecimal.valueOf(100.00);
                    boolean processed = simulateExternalPaymentGateway(amount);
                    if (!processed) {
                        throw new PaymentProcessingException("El gateway de pago rechazó la transacción");
                    }
                    PaymentStatus finalStatus = PaymentStatus.PROCESSED;
                    idempotencyRepository.updateStatus(idempotencyKey, finalStatus.name());
                    paymentRepository.savePayment(idempotencyKey, amount, finalStatus.name(),
                        "PAYMENT_" + UUID.randomUUID().toString().substring(0, 8).toUpperCase());
                    return finalStatus;
                })
                .subscribeOn(Schedulers.boundedElastic())
                .onErrorResume(PaymentProcessingException.class, error -> {
                    log.error("Error en procesamiento de pago, actualizando estado a FAILED: {}", error.getMessage());
                    return idempotencyRepository.updateStatus(idempotencyKey, PaymentStatus.FAILED.name())
                            .then(Mono.error(error));
                });
    }

    private boolean simulateExternalPaymentGateway(BigDecimal amount) {
        return amount.compareTo(BigDecimal.ZERO) > 0;
    }

    private Mono<PaymentStatus> fallbackProcessPayment(PaymentRequest paymentRequest, Throwable throwable) {
        log.error("Circuit breaker activado para paymentRequest: {}. Error: {}", 
            paymentRequest.idempotencyKey(), throwable.getMessage());
        return Mono.just(PaymentStatus.FAILED);
    }
}