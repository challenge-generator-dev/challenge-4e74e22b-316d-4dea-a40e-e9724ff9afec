package com.pragma.payments.infrastructure.adapters;

import com.pragma.payments.domain.model.PaymentStatus;
import com.pragma.payments.domain.ports.out.PaymentRepositoryPort;
import com.pragma.payments.infrastructure.persistence.entities.PaymentEntity;
import com.pragma.payments.infrastructure.persistence.repositories.PaymentJpaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Repository
@RequiredArgsConstructor
public class PaymentJpaAdapter implements PaymentRepositoryPort {

    private final PaymentJpaRepository paymentJpaRepository;

    @Override
    public Mono<PaymentStatus> savePayment(UUID idempotencyKey, BigDecimal amount, 
                                           String status, String externalReference) {
        return Mono.fromCallable(() -> {
                    log.debug("Persistiendo pago con idempotencyKey: {}", idempotencyKey);
                    PaymentEntity entity = PaymentEntity.builder()
                            .idempotencyKey(idempotencyKey.toString())
                            .amount(amount)
                            .status(status)
                            .externalReference(externalReference)
                            .createdAt(Instant.now())
                            .updatedAt(Instant.now())
                            .build();
                    PaymentEntity saved = paymentJpaRepository.save(entity);
                    log.info("Pago persistido exitosamente con ID: {}", saved.getId());
                    return PaymentStatus.valueOf(saved.getStatus());
                })
                .subscribeOn(Schedulers.boundedElastic())
                .onErrorResume(error -> {
                    log.error("Error al persistir pago: {}", error.getMessage());
                    return Mono.error(error);
                });
    }

    @Override
    public Mono<Optional<PaymentEntity>> findByIdempotencyKey(UUID idempotencyKey) {
        return Mono.fromCallable(() -> {
                    log.debug("Buscando pago por idempotencyKey: {}", idempotencyKey);
                    Optional<PaymentEntity> result = paymentJpaRepository
                            .findByIdempotencyKey(idempotencyKey.toString());
                    if (result.isPresent()) {
                        log.info("Pago encontrado para idempotencyKey: {}", idempotencyKey);
                    } else {
                        log.debug("No se encontró pago para idempotencyKey: {}", idempotencyKey);
                    }
                    return result;
                })
                .subscribeOn(Schedulers.boundedElastic());
    }

    @Override
    public Mono<PaymentStatus> updatePaymentStatus(UUID idempotencyKey, String status) {
        return Mono.fromCallable(() -> {
                    log.debug("Actualizando estado del pago para idempotencyKey: {} a estado: {}", 
                        idempotencyKey, status);
                    Optional<PaymentEntity> existingOpt = paymentJpaRepository
                            .findByIdempotencyKey(idempotencyKey.toString());
                    if (existingOpt.isEmpty()) {
                        throw new IllegalArgumentException(
                            "No se puede actualizar: pago no encontrado para key: " + idempotencyKey);
                    }
                    PaymentEntity existing = existingOpt.get();
                    existing.setStatus(status);
                    existing.setUpdatedAt(Instant.now());
                    PaymentEntity updated = paymentJpaRepository.save(existing);
                    log.info("Estado del pago actualizado a: {} para idempotencyKey: {}", 
                        status, idempotencyKey);
                    return PaymentStatus.valueOf(updated.getStatus());
                })
                .subscribeOn(Schedulers.boundedElastic())
                .onErrorResume(error -> {
                    log.error("Error al actualizar estado del pago: {}", error.getMessage());
                    return Mono.error(error);
                });
    }
}