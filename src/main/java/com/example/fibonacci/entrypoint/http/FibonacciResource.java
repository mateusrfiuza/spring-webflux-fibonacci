package com.example.fibonacci.entrypoint.http;


import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

import java.math.BigInteger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.fibonacci.service.FibonacciCalculator;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import reactor.core.publisher.Mono;

@RestController
@Validated
@RequestMapping("/fib")
public class FibonacciResource {

    private static final Logger logger = LoggerFactory.getLogger(FibonacciResource.class);

    private final FibonacciCalculator service;

    public FibonacciResource(final FibonacciCalculator service) {
        this.service = service;
    }

    @GetMapping
    @ApiOperation(value = "Calculate Fibonacci")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Operation executed", response = BigInteger.class),
            @ApiResponse(code = 400, message = "Invalid input data")
    })
    public Mono<ResponseEntity<BigInteger>> calculate(
            @RequestParam(name = "n")
            @Min(1)
            @Max(50000)
            final Long value) {

        logger.info("Received request to calculate Fibonacci value:{}", value);

        return service
                .execute(value)
                .flatMap(lastValue ->
                     Mono.just(new ResponseEntity<>(lastValue, HttpStatus.OK))
        );
    }

}
