package com.ecommerce.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.ecommerce.dto.ApiResponse;
import com.ecommerce.service.PaymentService;

@RestController
@RequestMapping("/payment")
public class PaymentController {

    @Autowired
    private PaymentService service;

    @PostMapping("/{orderId}")
    public ResponseEntity<?> pay(@PathVariable Long orderId,
                                 @RequestBody Map<String, String> body) {

        String method = body.get("method");

        String result = service.processPayment(orderId, method);

        return ResponseEntity.ok(
                new ApiResponse<>("Payment processed", result)
        );
    }
}