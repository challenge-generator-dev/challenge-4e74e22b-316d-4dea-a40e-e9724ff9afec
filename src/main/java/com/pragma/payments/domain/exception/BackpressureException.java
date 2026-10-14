package com.pragma.payments.domain.exception;

import java.time.Instant;

/**
 * Excepcion lanzada cuando se detecta backpressure en alguno de los canales
 * de entrada de pagos. Indica que el sistema esta bajo carga excesiva y no
 * puede aceptar mas solicitudes en ese momento.
 * 
 * Esta excepcion es temporal y el cliente deberia reintentar la peticion
 * despues de un delay configurado.
 */
public class BackpressureException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private final String channelName;
    private final Instant detectedAt;
    private final int currentLoad;
    private final int maxCapacity;
    private final long retryAfterMillis;

    public BackpressureException(String channelName) {
        super("Backpressure detected on channel: " + channelName);
        this.channelName = channelName;
        this.detectedAt = Instant.now();
        this.currentLoad = 0;
        this.maxCapacity = 0;
        this.retryAfterMillis = calculateDefaultRetryDelay();
    }

    public BackpressureException(String channelName, int currentLoad, int maxCapacity) {
        super(String.format("Backpressure detected on channel '%s': currentLoad=%d, maxCapacity=%d", 
                channelName, currentLoad, maxCapacity));
        this.channelName = channelName;
        this.detectedAt = Instant.now();
        this.currentLoad = currentLoad;
        this.maxCapacity = maxCapacity;
        this.retryAfterMillis = calculateRetryDelay(currentLoad, maxCapacity);
    }

    public BackpressureException(String channelName, int currentLoad, int maxCapacity, Throwable cause) {
        super(String.format("Backpressure detected on channel '%s': currentLoad=%d, maxCapacity=%d", 
                channelName, currentLoad, maxCapacity), cause);
        this.channelName = channelName;
        this.detectedAt = Instant.now();
        this.currentLoad = currentLoad;
        this.maxCapacity = maxCapacity;
        this.retryAfterMillis = calculateRetryDelay(currentLoad, maxCapacity);
    }

    private static long calculateDefaultRetryDelay() {
        return 5000L;
    }

    private static long calculateRetryDelay(int currentLoad, int maxCapacity) {
        double loadFactor = (double) currentLoad / maxCapacity;
        if (loadFactor >= 0.9) {
            return 10000L;
        } else if (loadFactor >= 0.75) {
            return 5000L;
        } else {
            return 2000L;
        }
    }

    public String getChannelName() {
        return channelName;
    }

    public Instant getDetectedAt() {
        return detectedAt;
    }

    public int getCurrentLoad() {
        return currentLoad;
    }

    public int getMaxCapacity() {
        return maxCapacity;
    }

    public long getRetryAfterMillis() {
        return retryAfterMillis;
    }

    public double getLoadPercentage() {
        if (maxCapacity == 0) {
            return 0.0;
        }
        return (double) currentLoad / maxCapacity * 100.0;
    }

    public boolean isCritical() {
        return getLoadPercentage() >= 90.0;
    }

    @Override
    public String toString() {
        return String.format("BackpressureException{channel='%s', load=%.2f%%, retryAfter=%dms}",
                channelName, getLoadPercentage(), retryAfterMillis);
    }
}