package com.pragma.payments.application;

import com.pragma.payments.domain.exception.DuplicatePaymentException;
import com.pragma.payments.domain.exception.PaymentProcessingException;
import com.pragma.payments.domain.model.PaymentRequest;
import com.pragma.payments.domain.model.PaymentStatus;
import com.pragma.payments.domain.ports.out.IdempotencyRepositoryPort;
import com.pragma.payments.domain.ports.out.PaymentRepositoryPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("ProcessPaymentServiceTest - Pruebas unitarias del caso de uso de procesamiento de pagos")
class ProcessPaymentServiceTest {

    @Mock
    private PaymentRepositoryPort paymentRepository;

    @Mock
    private IdempotencyRepositoryPort idempotencyRepository;

    @InjectMocks
    private ProcessPaymentService processPaymentService;

    private PaymentRequest paymentRequest;
    private UUID idempotencyKey;

    @BeforeEach
    void setUp() {
        idempotencyKey = UUID.randomUUID();
        paymentRequest = new PaymentRequest(
            idempotencyKey,
            "ACC-12345",
            new BigDecimal("1000.00"),
            "USD",
            "PAYMENT",
            Instant.now()
        );
    }

    @Nested
    @DisplayName("Escenarios de procesamiento exitoso")
    class SuccessfulProcessing {

        @Test
        @DisplayName("Debe procesar pago exitosamente cuando no existe clave de idempotencia")
        void shouldProcessPaymentSuccessfully_WhenNoIdempotencyKeyExists() {
            when(idempotencyRepository.existsByIdempotencyKey(idempotencyKey)).thenReturn(Mono.just(false));
            when(paymentRepository.save(any())).thenReturn(Mono.empty());
            when(idempotencyRepository.save(any())).thenReturn(Mono.empty());

            StepVerifier.create(processPaymentService.processPayment(paymentRequest))
                .expectNext(PaymentStatus.PROCESSED)
                .verifyComplete();

            verify(paymentRepository).save(any());
            verify(idempotencyRepository).save(any());
        }

        @Test
        @DisplayName("Debe retornar estado PENDING cuando el procesamiento está en curso")
        void shouldReturnPendingStatus_WhenPaymentIsInProgress() {
            when(idempotencyRepository.existsByIdempotencyKey(idempotencyKey)).thenReturn(Mono.just(true));
            when(idempotencyRepository.findByIdempotencyKey(idempotencyKey))
                .thenReturn(Mono.just(PaymentStatus.PENDING));

            StepVerifier.create(processPaymentService.processPayment(paymentRequest))
                .expectNext(PaymentStatus.PENDING)
                .verifyComplete();

            verify(paymentRepository, never()).save(any());
        }

        @Test
        @DisplayName("Debe retornar estado PROCESSED cuando el pago ya fue procesado")
        void shouldReturnProcessedStatus_WhenPaymentAlreadyProcessed() {
            when(idempotencyRepository.existsByIdempotencyKey(idempotencyKey)).thenReturn(Mono.just(true));
            when(idempotencyRepository.findByIdempotencyKey(idempotencyKey))
                .thenReturn(Mono.just(PaymentStatus.PROCESSED));

            StepVerifier.create(processPaymentService.processPayment(paymentRequest))
                .expectNext(PaymentStatus.PROCESSED)
                .verifyComplete();

            verify(paymentRepository, never()).save(any());
        }
    }

    @Nested
    @DisplayName("Escenarios de manejo de errores")
    class ErrorHandling {

        @Test
        @DisplayName("Debe lanzar DuplicatePaymentException cuando la clave de idempotencia ya existe con estado FAILED")
        void shouldThrowDuplicatePaymentException_WhenIdempotencyKeyExistsWithFailedStatus() {
            when(idempotencyRepository.existsByIdempotencyKey(idempotencyKey)).thenReturn(Mono.just(true));
            when(idempotencyRepository.findByIdempotencyKey(idempotencyKey))
                .thenReturn(Mono.just(PaymentStatus.FAILED));

            StepVerifier.create(processPaymentService.processPayment(paymentRequest))
                .expectError(DuplicatePaymentException.class)
                .verify();
        }

        @Test
        @DisplayName("Debe lanzar PaymentProcessingException cuando falla el repositorio de pagos")
        void shouldThrowPaymentProcessingException_WhenPaymentRepositoryFails() {
            when(idempotencyRepository.existsByIdempotencyKey(idempotencyKey)).thenReturn(Mono.just(false));
            when(paymentRepository.save(any())).thenReturn(Mono.error(new RuntimeException("Database connection failed")));

            StepVerifier.create(processPaymentService.processPayment(paymentRequest))
                .expectError(PaymentProcessingException.class)
                .verify();
        }

        @Test
        @DisplayName("Debe lanzar PaymentProcessingException cuando falla el repositorio de idempotencia")
        void shouldThrowPaymentProcessingException_WhenIdempotencyRepositoryFails() {
            when(idempotencyRepository.existsByIdempotencyKey(idempotencyKey)).thenReturn(Mono.just(false));
            when(paymentRepository.save(any())).thenReturn(Mono.empty());
            when(idempotencyRepository.save(any()))
                .thenReturn(Mono.error(new RuntimeException("Idempotency save failed")));

            StepVerifier.create(processPaymentService.processPayment(paymentRequest))
                .expectError(PaymentProcessingException.class)
                .verify();
        }
    }

    @Nested
    @DisplayName("Escenarios de verificación de estado")
    class StatusCheck {

        @Test
        @DisplayName("Debe retornar el estado del pago existente")
        void shouldReturnExistingPaymentStatus() {
            UUID existingKey = UUID.randomUUID();
            when(idempotencyRepository.findByIdempotencyKey(existingKey))
                .thenReturn(Mono.just(PaymentStatus.PROCESSED));

            StepVerifier.create(processPaymentService.checkPaymentStatus(existingKey))
                .expectNext(PaymentStatus.PROCESSED)
                .verifyComplete();
        }

        @Test
        @DisplayName("Debe retornar PENDING cuando no existe la clave de idempotencia")
        void shouldReturnPending_WhenIdempotencyKeyDoesNotExist() {
            UUID nonExistingKey = UUID.randomUUID();
            when(idempotencyRepository.findByIdempotencyKey(nonExistingKey))
                .thenReturn(Mono.empty());

            StepVerifier.create(processPaymentService.checkPaymentStatus(nonExistingKey))
                .expectNext(PaymentStatus.PENDING)
                .verifyComplete();
        }
    }

    @Nested
    @DisplayName("Validación de comportamiento")
    class BehaviorValidation {

        @Test
        @DisplayName("Debe invocar el repositorio de idempotencia antes de guardar el pago")
        void shouldCheckIdempotencyBeforeSavingPayment() {
            when(idempotencyRepository.existsByIdempotencyKey(idempotencyKey)).thenReturn(Mono.just(false));
            when(paymentRepository.save(any())).thenReturn(Mono.empty());
            when(idempotencyRepository.save(any())).thenReturn(Mono.empty());

            processPaymentService.processPayment(paymentRequest).block();

            var inOrder = org.mockito.Mockito.inOrder(idempotencyRepository, paymentRepository);
            inOrder.verify(idempotencyRepository).existsByIdempotencyKey(idempotencyKey);
            inOrder.verify(paymentRepository).save(any());
        }

        @Test
        @DisplayName("Debe guardar la clave de idempotencia con el estado correcto")
        void shouldSaveIdempotencyKeyWithCorrectStatus() {
            ArgumentCaptor<com.pragma.payments.domain.model.IdempotencyRecord> captor =
                ArgumentCaptor.forClass(com.pragma.payments.domain.model.IdempotencyRecord.class);

            when(idempotencyRepository.existsByIdempotencyKey(idempotencyKey)).thenReturn(Mono.just(false));
            when(paymentRepository.save(any())).thenReturn(Mono.empty());
            when(idempotencyRepository.save(captor.capture())).thenReturn(Mono.empty());

            processPaymentService.processPayment(paymentRequest).block();

            var savedRecord = captor.getValue();
            assertThat(savedRecord.idempotencyKey()).isEqualTo(idempotencyKey);
            assertThat(savedRecord.status()).isEqualTo(PaymentStatus.PROCESSED);
        }
    }
}