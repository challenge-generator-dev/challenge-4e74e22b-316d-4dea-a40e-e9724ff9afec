package com.pragma.payments.domain.model;

/**
 * Estados posibles de una solicitud de pago.
 */
public enum PaymentStatus {
    PENDING("Pago en proceso, esperando confirmación"),
    PROCESSED("Pago procesado exitosamente"),
    FAILED("Pago fallido, no se pudo procesar");

    private final String description;

    PaymentStatus(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}