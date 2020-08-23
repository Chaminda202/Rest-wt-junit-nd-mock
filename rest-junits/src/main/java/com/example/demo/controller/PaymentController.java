package com.example.demo.controller;

import com.example.demo.enumeration.PaymentStatusEnum;
import com.example.demo.model.request.PaymentRequest;
import com.example.demo.model.response.PaymentResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@RestController
@RequestMapping(value = "/${spring.application.version}/payments")
public class PaymentController {

    @PostMapping
    public ResponseEntity<PaymentResponse> makePayment(@Valid @RequestBody PaymentRequest request) {
        return ResponseEntity.ok().body(PaymentResponse.builder()
                .paymentId(UUID.randomUUID().toString())
                .status(PaymentStatusEnum.SUCCESS)
                .build());
    }

    @PutMapping(value = "/{paymentId}")
    public ResponseEntity<PaymentResponse> updatePayment(@PathVariable(value = "paymentId") @NotNull String paymentId, @Valid @RequestBody PaymentRequest request) {
        return ResponseEntity.ok().body(PaymentResponse.builder()
                .paymentId(paymentId)
                .status(request.getCardNumber().startsWith("1111") ? PaymentStatusEnum.SUCCESS : PaymentStatusEnum.SUCCESS)
                .build());
    }
}
