package com.ecommerce.service;

public interface PaymentService 
{
    String processPayment(Long orderId, String paymentMethod);
}