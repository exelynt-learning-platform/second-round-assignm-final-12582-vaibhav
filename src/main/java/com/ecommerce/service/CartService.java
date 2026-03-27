package com.ecommerce.service;

import com.ecommerce.entity.Cart;

public interface CartService 
{

	Cart addToCart(String email, Long productId, int qty);

    Cart getCart(String email);

    Cart removeItem(String email, Long cartItemId);
}
