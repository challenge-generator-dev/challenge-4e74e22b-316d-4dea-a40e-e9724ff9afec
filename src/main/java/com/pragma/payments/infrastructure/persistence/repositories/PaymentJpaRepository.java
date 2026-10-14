package com.pragma.payments.infrastructure.persistence.repositories;

import com.pragma.payments.infrastructure.persistence.entities.PaymentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface PaymentJpaRepository extends JpaRepository<PaymentEntity, UUID> {

    Optional<PaymentEntity> findByExternalId(String externalId);

    List<PaymentEntity> findByStatus(PaymentEntity.PaymentEntityStatus status);

    List<PaymentEntity> findByChannel(String channel);

    @Query("SELECT p FROM PaymentEntity p WHERE p.status = :status AND p.createdAt < :before")
    List<PaymentEntity> findStalePayments(
            @Param("status") PaymentEntity.PaymentEntityStatus status,
            @Param("before") Instant before
    );

    @Query("SELECT p FROM PaymentEntity p WHERE p.status = :status AND p.createdAt BETWEEN :from AND :to")
    List<PaymentEntity> findByStatusAndDateRange(
            @Param("status") PaymentEntity.PaymentEntityStatus status,
            @Param("from") Instant from,
            @Param("to") Instant to
    );

    @Query("SELECT COUNT(p) FROM PaymentEntity p WHERE p.status = :status")
    Long countByStatus(@Param("status") PaymentEntity.PaymentEntityStatus status);

    boolean existsByExternalId(String externalId);

    @Query("SELECT p FROM PaymentEntity p WHERE p.idempotencyKey = :idempotencyKey")
    Optional<PaymentEntity> findByIdempotencyKey(@Param("idempotencyKey") String idempotencyKey);
}