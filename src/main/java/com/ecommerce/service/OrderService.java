package com.ecommerce.service;

import java.util.List;

import com.ecommerce.entity.Cart;
import com.ecommerce.entity.Order;

public interface OrderService {

    Order createOrder(String email, String address);

    List<Order> getUserOrders(String email);
}
