package com.example.fibonacci.service;

import java.math.BigInteger;

import org.junit.jupiter.api.Test;

import com.example.fibonacci.service.exception.InvalidNumberException;

import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.*;

class FibonacciCalculatorTest {

    private final FibonacciCalculator subject = new FibonacciCalculator();

    @Test
    void shouldReturnRightResult() {

        // Given
        final var validValue = 72L;
        final var expectedResult = BigInteger.valueOf(498454011879264L);

        // When
        final var monoResult = subject.execute(validValue);

        // Then
        StepVerifier
                .create(monoResult)
                .consumeNextWith(result -> {
                    assertEquals(result, expectedResult);
                })
                .verifyComplete();

    }

    @Test
    void giveInvalidNumberShouldReturnException() {

        // Given
        final var invalidValue = 0L;

        // When
        final var result = subject.execute(invalidValue);

        // Then
        StepVerifier
                .create(result)
                .expectError(InvalidNumberException.class)
                .verify();

    }



}