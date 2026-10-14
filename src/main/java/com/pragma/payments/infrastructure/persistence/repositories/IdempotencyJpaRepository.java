package com.pragma.payments.infrastructure.persistence.repositories;

import com.pragma.payments.infrastructure.persistence.entities.IdempotencyEntity;
import com.pragma.payments.infrastructure.persistence.entities.IdempotencyEntity.IdempotencyStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Repository
public interface IdempotencyJpaRepository extends JpaRepository<IdempotencyEntity, java.util.UUID> {

    Optional<IdempotencyEntity> findByIdempotencyKey(String idempotencyKey);

    boolean existsByIdempotencyKey(String idempotencyKey);

    List<IdempotencyEntity> findByStatus(IdempotencyStatus status);

    @Query("SELECT i FROM IdempotencyEntity i WHERE i.status = :status AND i.expiresAt < :now")
    List<IdempotencyEntity> findExpiredWithStatus(
            @Param("status") IdempotencyStatus status,
            @Param("now") Instant now
    );

    @Query("SELECT i FROM IdempotencyEntity i WHERE i.status = 'PENDING' AND i.expiresAt > :now ORDER BY i.createdAt ASC")
    List<IdempotencyEntity> findPendingKeysNotYetExpired(@Param("now") Instant now);

    @Query("SELECT i FROM IdempotencyEntity i WHERE i.status = 'PROCESSING' AND i.lastRetryAt < :threshold")
    List<IdempotencyEntity> findStaleProcessingKeys(@Param("threshold") Instant threshold);

    @Modifying
    @Query("UPDATE IdempotencyEntity i SET i.status = 'EXPIRED' WHERE i.status = 'PENDING' AND i.expiresAt < :now")
    int markExpiredPendingKeys(@Param("now") Instant now);

    @Modifying
    @Query("UPDATE IdempotencyEntity i SET i.status = 'PENDING', i.retryCount = 0 WHERE i.id = :id")
    int resetToPending(@Param("id") java.util.UUID id);

    @Query("SELECT COUNT(i) FROM IdempotencyEntity i WHERE i.status = :status")
    Long countByStatus(@Param("status") IdempotencyStatus status);

    @Query("SELECT i FROM IdempotencyEntity i WHERE i.requestHash = :hash AND i.status = 'COMPLETED' AND i.expiresAt > :now")
    Optional<IdempotencyEntity> findCompletedByRequestHash(
            @Param("hash") String requestHash,
            @Param("now") Instant now
    );
}