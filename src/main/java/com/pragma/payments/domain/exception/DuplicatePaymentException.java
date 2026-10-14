package com.pragma.payments.domain.exception;

import java.util.UUID;

/**
 * Excepción lanzada cuando se detecta un intento de procesar
 * un pago que ya fue procesado anteriormente.
 * 
 * Esta excepción indica que existe una clave de idempotencia duplicada
 * en el sistema, lo cual significa que el cliente está intentando
 * reprocesar una solicitud que ya fue aceptada.
 */
public class DuplicatePaymentException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private final UUID idempotencyKey;

    /**
     * Constructor con mensaje de error y clave de idempotencia.
     *
     * @param message descripción del error
     * @param idempotencyKey clave de idempotencia duplicada
     */
    public DuplicatePaymentException(String message, UUID idempotencyKey) {
        super(message);
        this.idempotencyKey = idempotencyKey;
    }

    /**
     * Constructor con mensaje de error, clave de idempotencia y causa raíz.
     *
     * @param message descripción del error
     * @param idempotencyKey clave de idempotencia duplicada
     * @param cause excepción que causó este error
     */
    public DuplicatePaymentException(String message, UUID idempotencyKey, Throwable cause) {
        super(message, cause);
        this.idempotencyKey = idempotencyKey;
    }

    /**
     * Constructor solo con clave de idempotencia.
     * Genera un mensaje automático con la clave.
     *
     * @param idempotencyKey clave de idempotencia duplicada
     */
    public DuplicatePaymentException(UUID idempotencyKey) {
        super("Payment with idempotency key " + idempotencyKey + " already exists");
        this.idempotencyKey = idempotencyKey;
    }

    /**
     * Obtiene la clave de idempotencia asociada a esta excepción.
     *
     * @return la clave de idempotencia duplicada
     */
    public UUID getIdempotencyKey() {
        return idempotencyKey;
    }

    @Override
    public String toString() {
        return "DuplicatePaymentException{" +
                "idempotencyKey=" + idempotencyKey +
                ", message=" + getMessage() +
                '}';
    }
}