package com.ecommerce.service.implementation;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecommerce.entity.Order;
import com.ecommerce.entity.OrderStatus;
import com.ecommerce.exception.OrderNotFoundException;
import com.ecommerce.exception.PaymentFailedException;
import com.ecommerce.repositories.OrderRepository;
import com.ecommerce.service.PaymentService;

@Service
public class PaymentServiceImpl implements PaymentService {

    @Autowired
    private OrderRepository orderRepo;

    @Override
    public String processPayment(Long orderId, String method) {

        Order order = orderRepo.findById(orderId)
                .orElseThrow(() -> new OrderNotFoundException("Order not found"));

        if (order.getStatus() == OrderStatus.PAID) {
            throw new PaymentFailedException("Order already paid");
        }

        boolean success = new Random().nextBoolean();

        if (!success) {
            order.setStatus(OrderStatus.FAILED);
            orderRepo.save(order);
            throw new PaymentFailedException("Payment failed via " + method);
        }

        order.setStatus(OrderStatus.PAID);
        orderRepo.save(order);

        return "Payment successful via " + method;
    }
}