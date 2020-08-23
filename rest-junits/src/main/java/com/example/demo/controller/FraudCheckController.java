package com.example.demo.controller;

import com.example.demo.model.request.FraudCheckRequest;
import com.example.demo.model.response.FraudCheckResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "${spring.application.version}")
public class FraudCheckController {
    @PostMapping(value = "fraudCheck")
    public ResponseEntity<FraudCheckResponse> makePayment(@Valid @RequestBody FraudCheckRequest request) {
        return ResponseEntity.ok().body(FraudCheckResponse.builder()
                .blacklisted(request.getCardNumber().startsWith("1111") ? true : false)
                .build());
    }
}
