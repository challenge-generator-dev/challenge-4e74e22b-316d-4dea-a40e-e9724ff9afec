package com.pragma.payments.domain.exception;

/**
 * Excepcion raiz para errores durante el procesamiento de pagos.
 * Se lanza cuando ocurre un fallo inesperado en el flujo de pago
 * que no corresponde a una condicion de negocio especifica.
 */
public class PaymentProcessingException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private final String paymentId;
    private final String channel;

    public PaymentProcessingException(String message) {
        super(message);
        this.paymentId = null;
        this.channel = null;
    }

    public PaymentProcessingException(String message, Throwable cause) {
        super(message, cause);
        this.paymentId = null;
        this.channel = null;
    }

    public PaymentProcessingException(String message, String paymentId, String channel) {
        super(message);
        this.paymentId = paymentId;
        this.channel = channel;
    }

    public PaymentProcessingException(String message, String paymentId, String channel, Throwable cause) {
        super(message, cause);
        this.paymentId = paymentId;
        this.channel = channel;
    }

    public String getPaymentId() {
        return paymentId;
    }

    public String getChannel() {
        return channel;
    }

    @Override
    public String getMessage() {
        StringBuilder sb = new StringBuilder(super.getMessage());
        if (paymentId != null || channel != null) {
            sb.append(" [");
            if (paymentId != null) {
                sb.append("paymentId=").append(paymentId);
            }
            if (channel != null) {
                if (paymentId != null) {
                    sb.append(", ");
                }
                sb.append("channel=").append(channel);
            }
            sb.append("]");
        }
        return sb.toString();
    }

    public boolean hasPaymentContext() {
        return paymentId != null || channel != null;
    }
}