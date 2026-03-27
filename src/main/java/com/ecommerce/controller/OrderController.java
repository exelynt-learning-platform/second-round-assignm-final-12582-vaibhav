package com.ecommerce.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.ecommerce.dto.ApiResponse;
import com.ecommerce.dto.OrderDTO;
import com.ecommerce.entity.Order;
import com.ecommerce.entity.User;
import com.ecommerce.service.OrderService;
import com.ecommerce.util.Mapper;

@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderService service;

    // ✅ CREATE ORDER
    @PostMapping("/create")
    public ResponseEntity<?> createOrder(@RequestParam String address,
                                         Authentication auth) {

        String email = auth.getName();

        Order order = service.createOrder(email, address);

        return ResponseEntity.ok(
                new ApiResponse<>("Order created", Mapper.toOrderDTO(order))
        );
    }

    // ✅ GET ORDERS
    @GetMapping
    public ResponseEntity<?> getUserOrders(Authentication auth) {

        User user = (User) auth.getPrincipal();

        List<OrderDTO> orders = service.getUserOrders(user.getEmail())
                .stream()
                .map(Mapper::toOrderDTO)
                .toList();

        return ResponseEntity.ok(
                new ApiResponse<>("Orders fetched", orders)
        );
    }
}