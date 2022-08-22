package com.example.fibonacci.service;

import java.math.BigInteger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.example.fibonacci.service.exception.InvalidNumberException;

import reactor.core.publisher.Mono;

@Service
public class FibonacciCalculator {

    private static final Logger logger = LoggerFactory.getLogger(FibonacciCalculator.class);

    /**
     * Receive a number and calculate the last sequence of Fibonacci
     * @param value number greater than 0
     * @return Mono<BigInteger> AreaPaymentMethodStatusChangeRejected instance decoded from the given buffer
     * @throws com.example.fibonacci.service.exception.InvalidNumberException if a number with value less than 1
     */
    public Mono<BigInteger> execute(final Long value) {
        return this.validate(value)
                   .thenReturn(calculate(value))
                   .onErrorResume(throwable -> {
                        logger.error("Error calculating value:{}", value, throwable);
                        return Mono.error(throwable);
                });
    }

    private Mono<Void> validate(final Long value) {
        return Mono.fromCallable(() -> {
            if (null == value || value < 1) {
                throw new InvalidNumberException();
            }
            return null;
        });
    }

    private static BigInteger calculate(final Long n) {
        var previousNumber = new BigInteger(String.valueOf(0));
        var nextNumber = new BigInteger(String.valueOf(1));

        BigInteger sumPreviousNextNumber;

        for (int i = 2; i <= n; i++) {
            sumPreviousNextNumber = previousNumber.add(nextNumber);
            previousNumber = nextNumber;
            nextNumber = sumPreviousNextNumber;
        }

        final BigInteger result;
        result = nextNumber;

        return result;

    }

}
