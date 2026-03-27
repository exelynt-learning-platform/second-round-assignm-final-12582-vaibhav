package com.ecommerce.service.implementation;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecommerce.entity.*;
import com.ecommerce.repositories.CartRepository;
import com.ecommerce.repositories.OrderRepository;
import com.ecommerce.repositories.UserRepository;
import com.ecommerce.service.OrderService;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public Order createOrder(String email, String address) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Cart cart = cartRepository.findByUser(user)
                .orElseThrow(() -> new RuntimeException("Cart not found"));

        if (cart.getItems().isEmpty()) {
            throw new RuntimeException("Cart is empty");
        }

        Order order = new Order();
        order.setUser(user);
        order.setAddress(address);
        order.setStatus(OrderStatus.PENDING); // ✅ ENUM

        List<OrderItem> items = new ArrayList<>();
        double total = 0;

        for (CartItem ci : cart.getItems()) {

            Product product = ci.getProduct();

            // ✅ Stock validation
            if (product.getStock() < ci.getQuantity()) {
                throw new RuntimeException("Stock not available for " + product.getName());
            }

            // ✅ Reduce stock
            product.setStock(product.getStock() - ci.getQuantity());

            OrderItem oi = new OrderItem();
            oi.setProduct(product); // ✅ FIXED
            oi.setQuantity(ci.getQuantity());
            oi.setPrice(product.getPrice());
            oi.setOrder(order); // ✅ VERY IMPORTANT

            total += ci.getQuantity() * product.getPrice();
            items.add(oi);
        }

        order.setItems(items);
        order.setTotalPrice(total);

        // ✅ Clear cart after order
        cart.getItems().clear();
        cartRepository.save(cart);

        return orderRepository.save(order);
    }

    @Override
    public List<Order> getUserOrders(String email) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return orderRepository.findByUser(user);
    }
}