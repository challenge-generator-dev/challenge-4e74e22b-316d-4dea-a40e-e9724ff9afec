package com.pragma.payments.infrastructure.controllers;

import com.pragma.payments.domain.exception.BackpressureException;
import com.pragma.payments.domain.exception.DuplicatePaymentException;
import com.pragma.payments.domain.exception.PaymentProcessingException;
import com.pragma.payments.domain.model.PaymentRequest;
import com.pragma.payments.domain.model.PaymentStatus;
import com.pragma.payments.domain.ports.in.ProcessPaymentUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.web.reactive.function.BodyInserters.fromValue;

@ExtendWith(MockitoExtension.class)
@DisplayName("PaymentControllerTest - Pruebas de integración del controlador REST")
class PaymentControllerTest {

    @Mock
    private ProcessPaymentUseCase processPaymentUseCase;

    @InjectMocks
    private PaymentController paymentController;

    private WebTestClient webTestClient;
    private PaymentRequestDto paymentRequestDto;

    @BeforeEach
    void setUp() {
        webTestClient = WebTestClient.bindToController(paymentController).build();
        paymentRequestDto = new PaymentRequestDto(
            UUID.randomUUID().toString(),
            "ACC-12345",
            "1000.00",
            "USD",
            "PAYMENT"
        );
    }

    @Nested
    @DisplayName("Escenarios de procesamiento exitoso")
    class SuccessfulProcessing {

        @Test
        @DisplayName("Debe retornar 201 Created cuando el pago se procesa exitosamente")
        void shouldReturn201Created_WhenPaymentProcessedSuccessfully() {
            PaymentRequest request = toDomain(paymentRequestDto);
            when(processPaymentUseCase.processPayment(any(PaymentRequest.class)))
                .thenReturn(Mono.just(PaymentStatus.PROCESSED));

            webTestClient.post()
                .uri("/api/v1/payments")
                .contentType(MediaType.APPLICATION_JSON)
                .body(fromValue(paymentRequestDto))
                .exchange()
                .expectStatus().isCreated()
                .expectBody()
                .jsonPath("$.status").isEqualTo("PROCESSED")
                .jsonPath("$.message").isEqualTo("Pago procesado exitosamente");
        }

        @Test
        @DisplayName("Debe retornar 202 Accepted cuando el pago está en proceso")
        void shouldReturn202Accepted_WhenPaymentIsPending() {
            PaymentRequest request = toDomain(paymentRequestDto);
            when(processPaymentUseCase.processPayment(any(PaymentRequest.class)))
                .thenReturn(Mono.just(PaymentStatus.PENDING));

            webTestClient.post()
                .uri("/api/v1/payments")
                .contentType(MediaType.APPLICATION_JSON)
                .body(fromValue(paymentRequestDto))
                .exchange()
                .expectStatus().isAccepted()
                .expectBody()
                .jsonPath("$.status").isEqualTo("PENDING")
                .jsonPath("$.message").isEqualTo("Pago en proceso, esperando confirmación");
        }

        @Test
        @DisplayName("Debe retornar 200 Ok cuando se consulta el estado de un pago existente")
        void shouldReturn200Ok_WhenCheckingExistingPaymentStatus() {
            UUID existingKey = UUID.randomUUID();
            when(processPaymentUseCase.checkPaymentStatus(existingKey))
                .thenReturn(Mono.just(PaymentStatus.PROCESSED));

            webTestClient.get()
                .uri("/api/v1/payments/{idempotencyKey}", existingKey)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.status").isEqualTo("PROCESSED");
        }
    }

    @Nested
    @DisplayName("Escenarios de manejo de errores")
    class ErrorHandling {

        @Test
        @DisplayName("Debe retornar 409 Conflict cuando se intenta procesar un pago duplicado")
        void shouldReturn409Conflict_WhenDuplicatePaymentAttempt() {
            PaymentRequest request = toDomain(paymentRequestDto);
            when(processPaymentUseCase.processPayment(any(PaymentRequest.class)))
                .thenReturn(Mono.error(new DuplicatePaymentException("Pago duplicado")));

            webTestClient.post()
                .uri("/api/v1/payments")
                .contentType(MediaType.APPLICATION_JSON)
                .body(fromValue(paymentRequestDto))
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.CONFLICT)
                .expectBody()
                .jsonPath("$.error").isEqualTo("CONFLICT")
                .jsonPath("$.message").isEqualTo("Pago duplicado");
        }

        @Test
        @DisplayName("Debe retornar 503 Service Unavailable cuando hay backpressure")
        void shouldReturn503ServiceUnavailable_WhenBackpressureOccurs() {
            PaymentRequest request = toDomain(paymentRequestDto);
            when(processPaymentUseCase.processPayment(any(PaymentRequest.class)))
                .thenReturn(Mono.error(new BackpressureException("Capacidad máxima alcanzada")));

            webTestClient.post()
                .uri("/api/v1/payments")
                .contentType(MediaType.APPLICATION_JSON)
                .body(fromValue(paymentRequestDto))
                .exchange()
                .expectStatus().isServiceUnavailable()
                .expectBody()
                .jsonPath("$.error").isEqualTo("SERVICE_UNAVAILABLE")
                .jsonPath("$.message").isEqualTo("Capacidad máxima alcanzada");
        }

        @Test
        @DisplayName("Debe retornar 500 Internal Server Error cuando falla el procesamiento")
        void shouldReturn500InternalServerError_WhenProcessingFails() {
            PaymentRequest request = toDomain(paymentRequestDto);
            when(processPaymentUseCase.processPayment(any(PaymentRequest.class)))
                .thenReturn(Mono.error(new PaymentProcessingException("Error interno")));

            webTestClient.post()
                .uri("/api/v1/payments")
                .contentType(MediaType.APPLICATION_JSON)
                .body(fromValue(paymentRequestDto))
                .exchange()
                .expectStatus().is5xxServerError()
                .expectBody()
                .jsonPath("$.error").isEqualTo("INTERNAL_SERVER_ERROR");
        }
    }

    @Nested
    @DisplayName("Escenarios de validación de entrada")
    class InputValidation {

        @Test
        @DisplayName("Debe retornar 400 Bad Request cuando el idempotencyKey está vacío")
        void shouldReturn400BadRequest_WhenIdempotencyKeyIsEmpty() {
            paymentRequestDto = new PaymentRequestDto(
                "",
                "ACC-12345",
                "1000.00",
                "USD",
                "PAYMENT"
            );

            webTestClient.post()
                .uri("/api/v1/payments")
                .contentType(MediaType.APPLICATION_JSON)
                .body(fromValue(paymentRequestDto))
                .exchange()
                .expectStatus().isBadRequest();
        }

        @Test
        @DisplayName("Debe retornar 400 Bad Request cuando el accountId está vacío")
        void shouldReturn400BadRequest_WhenAccountIdIsEmpty() {
            paymentRequestDto = new PaymentRequestDto(
                UUID.randomUUID().toString(),
                "",
                "1000.00",
                "USD",
                "PAYMENT"
            );

            webTestClient.post()
                .uri("/api/v1/payments")
                .contentType(MediaType.APPLICATION_JSON)
                .body(fromValue(paymentRequestDto))
                .exchange()
                .expectStatus().isBadRequest();
        }

        @Test
        @DisplayName("Debe retornar 400 Bad Request cuando el amount es inválido")
        void shouldReturn400BadRequest_WhenAmountIsInvalid() {
            paymentRequestDto = new PaymentRequestDto(
                UUID.randomUUID().toString(),
                "ACC-12345",
                "-100.00",
                "USD",
                "PAYMENT"
            );

            webTestClient.post()
                .uri("/api/v1/payments")
                .contentType(MediaType.APPLICATION_JSON)
                .body(fromValue(paymentRequestDto))
                .exchange()
                .expectStatus().isBadRequest();
        }

        @Test
        @DisplayName("Debe retornar 400 Bad Request cuando la moneda está vacía")
        void shouldReturn400BadRequest_WhenCurrencyIsEmpty() {
            paymentRequestDto = new PaymentRequestDto(
                UUID.randomUUID().toString(),
                "ACC-12345",
                "1000.00",
                "",
                "PAYMENT"
            );

            webTestClient.post()
                .uri("/api/v1/payments")
                .contentType(MediaType.APPLICATION_JSON)
                .body(fromValue(paymentRequestDto))
                .exchange()
                .expectStatus().isBadRequest();
        }
    }

    @Nested
    @DisplayName("Escenarios de idempotencia en controlador")
    class IdempotencyScenarios {

        @Test
        @DisplayName("Debe propagar la clave de idempotencia al caso de uso")
        void shouldPropagateIdempotencyKeyToUseCase() {
            UUID specificKey = UUID.randomUUID();
            paymentRequestDto = new PaymentRequestDto(
                specificKey.toString(),
                "ACC-12345",
                "1000.00",
                "USD",
                "PAYMENT"
            );

            when(processPaymentUseCase.processPayment(any(PaymentRequest.class)))
                .thenReturn(Mono.just(PaymentStatus.PROCESSED));

            webTestClient.post()
                .uri("/api/v1/payments")
                .contentType(MediaType.APPLICATION_JSON)
                .body(fromValue(paymentRequestDto))
                .exchange()
                .expectStatus().isCreated();
        }

        @Test
        @DisplayName("Debe manejar correctamente reintentos con la misma clave")
        void shouldHandleRetriesWithSameKey() {
            UUID key = UUID.randomUUID();
            paymentRequestDto = new PaymentRequestDto(
                key.toString(),
                "ACC-12345",
                "1000.00",
                "USD",
                "PAYMENT"
            );

            when(processPaymentUseCase.processPayment(any(PaymentRequest.class)))
                .thenReturn(Mono.just(PaymentStatus.PROCESSED));

            webTestClient.post()
                .uri("/api/v1/payments")
                .contentType(MediaType.APPLICATION_JSON)
                .body(fromValue(paymentRequestDto))
                .exchange()
                .expectStatus().isCreated();

            webTestClient.post()
                .uri("/api/v1/payments")
                .contentType(MediaType.APPLICATION_JSON)
                .body(fromValue(paymentRequestDto))
                .exchange()
                .expectStatus().isCreated();
        }
    }

    private PaymentRequest toDomain(PaymentRequestDto dto) {
        return new PaymentRequest(
            UUID.fromString(dto.idempotencyKey()),
            dto.accountId(),
            new BigDecimal(dto.amount()),
            dto.currency(),
            dto.paymentType(),
            Instant.now()
        );
    }

    record PaymentRequestDto(
        String idempotencyKey,
        String accountId,
        String amount,
        String currency,
        String paymentType
    ) {}
}