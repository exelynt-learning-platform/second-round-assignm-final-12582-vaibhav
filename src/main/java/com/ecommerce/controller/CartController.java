package com.ecommerce.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.ecommerce.dto.ApiResponse;
import com.ecommerce.entity.Cart;
import com.ecommerce.entity.User;
import com.ecommerce.service.CartService;

@RestController
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private CartService service;

    // ✅ ADD TO CART
    @PostMapping("/add")
    public ResponseEntity<?> add(@RequestParam Long productId,
                                @RequestParam int qty,
                                Authentication auth) {

        User user = (User) auth.getPrincipal();

        Cart cart = service.addToCart(user.getEmail(), productId, qty);

        return ResponseEntity.ok(
                new ApiResponse<>("Item added to cart", cart)
        );
    }

    // ✅ GET CART
    @GetMapping
    public ResponseEntity<?> getCart(Authentication auth) {

        User user = (User) auth.getPrincipal();

        Cart cart = service.getCart(user.getEmail());

        return ResponseEntity.ok(
                new ApiResponse<>("Cart fetched", cart)
        );
    }

    // ✅ REMOVE ITEM
    @DeleteMapping("/remove")
    public ResponseEntity<?> remove(@RequestParam Long cartItemId,
                                   Authentication auth) {

        User user = (User) auth.getPrincipal();

        Cart cart = service.removeItem(user.getEmail(), cartItemId);

        return ResponseEntity.ok(
                new ApiResponse<>("Item removed from cart", cart)
        );
    }
}