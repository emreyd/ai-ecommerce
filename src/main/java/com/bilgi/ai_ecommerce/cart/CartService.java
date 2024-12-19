package com.bilgi.ai_ecommerce.cart;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class CartService {

    private final CartRepository cartRepository;

    @Autowired
    public CartService(CartRepository cartRepository) {
        this.cartRepository = cartRepository;
    }

    public Cart getCartByUserId(String userId) {
        return cartRepository.findByUserId(userId)
                .orElseGet(() -> createEmptyCart(userId));
    }

    public Cart addItemToCart(String userId, CartItem cartItem) {
        Cart cart = getCartByUserId(userId);
        cart.getItems().add(cartItem);
        cart.setUpdatedAt(Instant.now().toString());
        return cartRepository.save(cart);
    }

    public Cart removeItemFromCart(String userId, String productId) {
        Cart cart = getCartByUserId(userId);
        cart.getItems().removeIf(item -> item.getProductId().equals(productId));
        cart.setUpdatedAt(Instant.now().toString());
        return cartRepository.save(cart);
    }

    private Cart createEmptyCart(String userId) {
        Cart newCart = new Cart();
        newCart.setCartId("cart_" + userId); // Example cart ID
        newCart.setUserId(userId);
        newCart.setCreatedAt(Instant.now().toString());
        newCart.setUpdatedAt(Instant.now().toString());
        return cartRepository.save(newCart);
    }
}
