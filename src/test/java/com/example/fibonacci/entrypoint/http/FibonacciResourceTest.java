package com.example.fibonacci.entrypoint.http;

import java.math.BigInteger;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;

import com.example.fibonacci.service.FibonacciCalculator;

import reactor.core.publisher.Mono;

import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@WebFluxTest(controllers = FibonacciResource.class)
class FibonacciResourceTest {

    @Autowired
    private WebTestClient webClient;

    @MockBean
    private FibonacciCalculator calculator;

    @Test
    void shouldProcessRequestWithSuccess() {

        // Given
        final var validNumber = 10L;
        final var expectedResult = "55";

        when(calculator.execute(10L)).thenReturn(Mono.just(new BigInteger(expectedResult)));

        // When
        final var response = webClient.get()
                                      .uri("/fib?n={value}", validNumber)
                                      .exchange();

        // Then
        response.expectStatus().isOk();
        response.expectBody().json(expectedResult);

    }

    @Test
    void shouldReturnBadRequestWithValueLessThanValueAccepted() {

        // Given
        final var invalidNumber = 0L;

        // When
        final var response = webClient.get()
                                      .uri("/fib?n={value}", invalidNumber)
                                      .exchange();

        // Then
        response.expectStatus().isBadRequest();
        response.expectBody()
                .jsonPath("$.status").isEqualTo("BAD_REQUEST")
                .jsonPath("$.message").isEqualTo("calculate.value: must be greater than or equal to 1");

    }

    @Test
    void shouldReturnBadRequestWithValueGreaterThanValueAccepted() {

        // Given
        final var invalidNumber = 50001L;

        // When
        final var response = webClient.get()
                                      .uri("/fib?n={value}", invalidNumber)
                                      .exchange();

        // Then
        response.expectStatus().isBadRequest();
        response.expectBody()
                .jsonPath("$.status").isEqualTo("BAD_REQUEST")
                .jsonPath("$.message").isEqualTo("calculate.value: must be less than or equal to 50000");

    }

    @Test
    void shouldReturnInternalServerError() {

        // Given
        final var validNumber = 10L;
        when(calculator.execute(10L)).thenThrow(new IllegalArgumentException("Error random"));

        // When
        final var response = webClient.get()
                                      .uri("/fib?n={value}", validNumber)
                                      .exchange();

        // Then
        response.expectStatus().is5xxServerError();
        response.expectBody()
                .jsonPath("$.status").isEqualTo("INTERNAL_SERVER_ERROR")
                .jsonPath("$.message").isEqualTo("Error random");

    }


}