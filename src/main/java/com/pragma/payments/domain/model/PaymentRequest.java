package com.pragma.payments.domain.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Representa una solicitud de pago recibida desde cualquier canal.
 * Contiene la información necesaria para procesar el pago de manera idempotente.
 */
public record PaymentRequest(
    @NotNull(message = "La clave de idempotencia no puede ser nula")
    UUID idempotencyKey,

    @NotNull(message = "El monto no puede ser nulo")
    @Positive(message = "El monto debe ser positivo")
    BigDecimal amount,

    @NotNull(message = "El canal no puede ser nulo")
    @Size(min = 3, max = 20, message = "El canal debe tener entre 3 y 20 caracteres")
    String channel,

    @NotNull(message = "La referencia del pago no puede ser nula")
    @Size(min = 1, max = 100, message = "La referencia debe tener entre 1 y 100 caracteres")
    String paymentReference,

    @NotNull(message = "El identificador del cliente no puede ser nulo")
    UUID customerId,

    LocalDateTime createdAt
) {
    /**
     * Constructor que inicializa la fecha de creación con el momento actual.
     */
    public PaymentRequest {
        if (createdAt == null) {
            createdAt = LocalDateTime.now();
        }
    }

    /**
     * Valida que la solicitud de pago tenga datos consistentes.
     * @throws IllegalArgumentException si algún campo obligatorio es inválido.
     */
    public void validate() {
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("El monto debe ser mayor que cero");
        }
        if (channel == null || channel.trim().isEmpty()) {
            throw new IllegalArgumentException("El canal no puede estar vacío");
        }
        if (paymentReference == null || paymentReference.trim().isEmpty()) {
            throw new IllegalArgumentException("La referencia del pago no puede estar vacía");
        }
    }
}