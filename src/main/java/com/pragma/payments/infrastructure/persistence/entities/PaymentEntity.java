package com.pragma.payments.infrastructure.persistence.entities;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

/**
 * Entidad JPA que representa un pago en la base de datos PostgreSQL.
 * Mapea la tabla 'payments' y almacena toda la informacion relacionada
 * con una transaccion de pago procesada por el sistema.
 */
@Entity
@Table(name = "payments", indexes = {
    @Index(name = "idx_payment_idempotency_key", columnList = "idempotencyKey"),
    @Index(name = "idx_payment_status", columnList = "status"),
    @Index(name = "idx_payment_created_at", columnList = "createdAt"),
    @Index(name = "idx_payment_channel", columnList = "channel")
})
public class PaymentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "idempotency_key", nullable = false, unique = true, length = 36)
    private String idempotencyKey;

    @Column(name = "amount", nullable = false, precision = 19, scale = 4)
    private BigDecimal amount;

    @Column(name = "currency", nullable = false, length = 3)
    private String currency;

    @Column(name = "payer_document_number", nullable = false, length = 20)
    private String payerDocumentNumber;

    @Column(name = "payer_document_type", nullable = false, length = 10)
    private String payerDocumentType;

    @Column(name = "payer_name", nullable = false, length = 200)
    private String payerName;

    @Column(name = "payee_document_number", nullable = false, length = 20)
    private String payeeDocumentNumber;

    @Column(name = "payee_document_type", nullable = false, length = 10)
    private String payeeDocumentType;

    @Column(name = "payee_name", nullable = false, length = 200)
    private String payeeName;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 20)
    private PaymentStatusEntity status;

    @Column(name = "channel", nullable = false, length = 20)
    private String channel;

    @Column(name = "description", length = 500)
    private String description;

    @Column(name = "external_reference", length = 100)
    private String externalReference;

    @Column(name = "failure_reason", length = 1000)
    private String failureReason;

    @Column(name = "processed_at")
    private Instant processedAt;

    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

    @Version
    @Column(name = "version")
    private Long version;

    public PaymentEntity() {
    }

    @PrePersist
    protected void onCreate() {
        Instant now = Instant.now();
        this.createdAt = now;
        this.updatedAt = now;
        if (this.status == null) {
            this.status = PaymentStatusEntity.PENDING;
        }
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = Instant.now();
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getIdempotencyKey() {
        return idempotencyKey;
    }

    public void setIdempotencyKey(String idempotencyKey) {
        this.idempotencyKey = idempotencyKey;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getPayerDocumentNumber() {
        return payerDocumentNumber;
    }

    public void setPayerDocumentNumber(String payerDocumentNumber) {
        this.payerDocumentNumber = payerDocumentNumber;
    }

    public String getPayerDocumentType() {
        return payerDocumentType;
    }

    public void setPayerDocumentType(String payerDocumentType) {
        this.payerDocumentType = payerDocumentType;
    }

    public String getPayerName() {
        return payerName;
    }

    public void setPayerName(String payerName) {
        this.payerName = payerName;
    }

    public String getPayeeDocumentNumber() {
        return payeeDocumentNumber;
    }

    public void setPayeeDocumentNumber(String payeeDocumentNumber) {
        this.payeeDocumentNumber = payeeDocumentNumber;
    }

    public String getPayeeDocumentType() {
        return payeeDocumentType;
    }

    public void setPayeeDocumentType(String payeeDocumentType) {
        this.payeeDocumentType = payeeDocumentType;
    }

    public String getPayeeName() {
        return payeeName;
    }

    public void setPayeeName(String payeeName) {
        this.payeeName = payeeName;
    }

    public PaymentStatusEntity getStatus() {
        return status;
    }

    public void setStatus(PaymentStatusEntity status) {
        this.status = status;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getExternalReference() {
        return externalReference;
    }

    public void setExternalReference(String externalReference) {
        this.externalReference = externalReference;
    }

    public String getFailureReason() {
        return failureReason;
    }

    public void setFailureReason(String failureReason) {
        this.failureReason = failureReason;
    }

    public Instant getProcessedAt() {
        return processedAt;
    }

    public void setProcessedAt(Instant processedAt) {
        this.processedAt = processedAt;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public void markAsProcessed() {
        this.status = PaymentStatusEntity.PROCESSED;
        this.processedAt = Instant.now();
    }

    public void markAsFailed(String reason) {
        this.status = PaymentStatusEntity.FAILED;
        this.failureReason = reason;
    }

    public boolean isProcessed() {
        return this.status == PaymentStatusEntity.PROCESSED;
    }

    public boolean isPending() {
        return this.status == PaymentStatusEntity.PENDING;
    }

    public boolean isFailed() {
        return this.status == PaymentStatusEntity.FAILED;
    }

    /**
     * Enum interno para el estado de persistencia.
     * Mantiene congruencia con PaymentStatus del dominio.
     */
    public enum PaymentStatusEntity {
        PENDING("Pago en proceso, esperando confirmacion"),
        PROCESSED("Pago procesado exitosamente"),
        FAILED("Pago fallido, no se pudo procesar");

        private final String description;

        PaymentStatusEntity(String description) {
            this.description = description;
        }

        public String getDescription() {
            return description;
        }
    }
}