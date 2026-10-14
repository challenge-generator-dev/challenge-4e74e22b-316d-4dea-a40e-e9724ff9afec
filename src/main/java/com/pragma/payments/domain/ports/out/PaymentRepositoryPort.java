package com.pragma.payments.domain.ports.out;

import com.pragma.payments.domain.model.PaymentRequest;
import com.pragma.payments.domain.model.PaymentStatus;
import reactor.core.publisher.Mono;

import java.util.UUID;

/**
 * Puerto de salida para la persistencia de pagos.
 * Define las operaciones de acceso a datos que el dominio necesita
 * para gestionar el ciclo de vida de los pagos.
 */
public interface PaymentRepositoryPort {

    /**
     * Persiste una nueva solicitud de pago en el sistema.
     *
     * @param paymentRequest la solicitud de pago a persistir
     * @param idempotencyKey clave que garantiza la idempotencia de la operación
     * @return Mono que emite el estado del pago una vez persistido
     */
    Mono<PaymentStatus> savePayment(PaymentRequest paymentRequest, UUID idempotencyKey);

    /**
     * Recupera el estado de un pago previamente procesado.
     *
     * @param idempotencyKey clave de idempotencia del pago a consultar
     * @return Mono que emite el estado del pago o vacío si no existe
     */
    Mono<PaymentStatus> findPaymentStatusByIdempotencyKey(UUID idempotencyKey);

    /**
     * Actualiza el estado de un pago existente.
     *
     * @param idempotencyKey clave de idempotencia del pago a actualizar
     * @param newStatus nuevo estado a establecer
     * @return Mono que emite el estado actualizado
     */
    Mono<PaymentStatus> updatePaymentStatus(UUID idempotencyKey, PaymentStatus newStatus);
}