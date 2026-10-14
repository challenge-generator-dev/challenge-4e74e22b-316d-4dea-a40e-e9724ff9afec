package com.pragma.payments.infrastructure.adapters;

import com.pragma.payments.domain.ports.out.IdempotencyRepositoryPort;
import com.pragma.payments.infrastructure.persistence.entities.IdempotencyEntity;
import com.pragma.payments.infrastructure.persistence.repositories.IdempotencyJpaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Repository
@RequiredArgsConstructor
public class IdempotencyJpaAdapter implements IdempotencyRepositoryPort {

    private final IdempotencyJpaRepository idempotencyJpaRepository;

    @Override
    public Mono<IdempotencyEntity> save(UUID idempotencyKey, String status, String createdAt) {
        return Mono.fromCallable(() -> {
                    log.debug("Guardando registro de idempotencia con key: {}", idempotencyKey);
                    IdempotencyEntity entity = IdempotencyEntity.builder()
                            .idempotencyKey(idempotencyKey.toString())
                            .status(status)
                            .createdAt(createdAt)
                            .processedAt(Instant.now().toString())
                            .build();
                    IdempotencyEntity saved = idempotencyJpaRepository.save(entity);
                    log.info("Registro de idempotencia guardado exitosamente: {}", saved.getId());
                    return saved;
                })
                .subscribeOn(Schedulers.boundedElastic())
                .onErrorResume(error -> {
                    log.error("Error al guardar registro de idempotencia: {}", error.getMessage());
                    return Mono.error(error);
                });
    }

    @Override
    public Mono<Optional<IdempotencyEntity>> findByIdempotencyKey(UUID idempotencyKey) {
        return Mono.fromCallable(() -> {
                    log.debug("Buscando registro de idempotencia por key: {}", idempotencyKey);
                    Optional<IdempotencyEntity> result = idempotencyJpaRepository
                            .findByIdempotencyKey(idempotencyKey.toString());
                    if (result.isPresent()) {
                        log.info("Registro de idempotencia encontrado para key: {}", idempotencyKey);
                    } else {
                        log.debug("No existe registro de idempotencia para key: {}", idempotencyKey);
                    }
                    return result;
                })
                .subscribeOn(Schedulers.boundedElastic());
    }

    @Override
    public Mono<IdempotencyEntity> updateStatus(UUID idempotencyKey, String status) {
        return Mono.fromCallable(() -> {
                    log.debug("Actualizando estado de idempotencia para key: {} a estado: {}", 
                        idempotencyKey, status);
                    Optional<IdempotencyEntity> existingOpt = idempotencyJpaRepository
                            .findByIdempotencyKey(idempotencyKey.toString());
                    if (existingOpt.isEmpty()) {
                        throw new IllegalArgumentException(
                            "No se puede actualizar: registro de idempotencia no encontrado para key: " 
                            + idempotencyKey);
                    }
                    IdempotencyEntity existing = existingOpt.get();
                    existing.setStatus(status);
                    existing.setProcessedAt(Instant.now().toString());
                    IdempotencyEntity updated = idempotencyJpaRepository.save(existing);
                    log.info("Estado de idempotencia actualizado a: {} para key: {}", status, idempotencyKey);
                    return updated;
                })
                .subscribeOn(Schedulers.boundedElastic())
                .onErrorResume(error -> {
                    log.error("Error al actualizar estado de idempotencia: {}", error.getMessage());
                    return Mono.error(error);
                });
    }

    @Override
    public Mono<Boolean> existsByIdempotencyKey(UUID idempotencyKey) {
        return Mono.fromCallable(() -> {
                    log.debug("Verificando existencia de registro de idempotencia para key: {}", idempotencyKey);
                    boolean exists = idempotencyJpaRepository
                            .existsByIdempotencyKey(idempotencyKey.toString());
                    log.debug("Existencia de registro para key {}: {}", idempotencyKey, exists);
                    return exists;
                })
                .subscribeOn(Schedulers.boundedElastic());
    }
}