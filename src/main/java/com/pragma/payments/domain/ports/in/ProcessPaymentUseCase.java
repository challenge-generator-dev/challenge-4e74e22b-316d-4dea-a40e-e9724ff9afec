package com.pragma.payments.domain.ports.in;

import com.pragma.payments.domain.model.PaymentRequest;
import com.pragma.payments.domain.model.PaymentStatus;
import reactor.core.publisher.Mono;

/**
 * Puerto de entrada para procesar pagos de manera idempotente.
 * Define el contrato que la capa de aplicación debe implementar.
 */
public interface ProcessPaymentUseCase {
    /**
     * Procesa una solicitud de pago de manera idempotente.
     * @param paymentRequest La solicitud de pago a procesar.
     * @return Mono con el estado del pago procesado.
     * @throws com.pragma.payments.domain.exception.DuplicatePaymentException si la clave de idempotencia ya existe.
     * @throws com.pragma.payments.domain.exception.PaymentProcessingException si ocurre un error durante el procesamiento.
     */
    Mono<PaymentStatus> processPayment(PaymentRequest paymentRequest);

    /**
     * Verifica el estado de una solicitud de pago existente.
     * @param idempotencyKey La clave de idempotencia de la solicitud.
     * @return Mono con el estado del pago si existe, o Mono.empty() si no existe.
     */
    Mono<PaymentStatus> checkPaymentStatus(UUID idempotencyKey);
}