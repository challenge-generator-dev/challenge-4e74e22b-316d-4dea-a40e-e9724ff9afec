package com.pragma.payments.domain.ports.out;

import com.pragma.payments.domain.model.PaymentRequest;
import reactor.core.publisher.Mono;

import java.time.Instant;
import java.util.UUID;

/**
 * Puerto de salida para la gestión de claves de idempotencia.
 * Garantiza que cada solicitud de pago sea procesada una sola vez,
 * evitando duplicaciones en caso de reintentos por parte del cliente.
 */
public interface IdempotencyRepositoryPort {

    /**
     * Verifica si una clave de idempotencia ya existe en el sistema.
     *
     * @param idempotencyKey clave a verificar
     * @return Mono que emite true si la clave existe, false en caso contrario
     */
    Mono<Boolean> existsByIdempotencyKey(UUID idempotencyKey);

    /**
     * Persiste una nueva clave de idempotencia junto con la solicitud
     * de pago asociada. Debe ejecutarse dentro de una transacción
     * que también persista el pago.
     *
     * @param idempotencyKey clave de idempotencia única
     * @param paymentRequest solicitud de pago asociada
     * @param expiresAt momento de expiración de la clave
     * @return Mono que completa cuando la operación succeeds
     */
    Mono<Void> saveIdempotencyKey(UUID idempotencyKey, PaymentRequest paymentRequest, Instant expiresAt);

    /**
     * Recupera la solicitud de pago asociada a una clave de idempotencia.
     *
     * @param idempotencyKey clave de idempotencia a consultar
     * @return Mono que emite la solicitud de pago original o vacío si no existe
     */
    Mono<PaymentRequest> findPaymentRequestByIdempotencyKey(UUID idempotencyKey);

    /**
     * Elimina una clave de idempotencia que ha expirado.
     *
     * @param idempotencyKey clave a eliminar
     * @return Mono que completa cuando la operación succeeds
     */
    Mono<Void> deleteExpiredIdempotencyKey(UUID idempotencyKey);
}