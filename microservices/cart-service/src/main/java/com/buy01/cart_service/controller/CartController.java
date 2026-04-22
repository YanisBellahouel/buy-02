package com.buy01.cart_service.controller;

import com.buy01.cart_service.model.Cart;
import com.buy01.cart_service.model.CartItem;
import com.buy01.cart_service.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    // GET /api/cart/{clientId}
    @GetMapping("/{clientId}")
    public ResponseEntity<Cart> getCart(@PathVariable String clientId) {
        return ResponseEntity.ok(cartService.getOrCreateCart(clientId));
    }

    // POST /api/cart/{clientId}/items
    @PostMapping("/{clientId}/items")
    public ResponseEntity<Cart> addItem(
            @PathVariable String clientId,
            @RequestBody CartItem item) {
        return ResponseEntity.ok(cartService.addItem(clientId, item));
    }

    // PUT /api/cart/{clientId}/items/{productId}
    @PutMapping("/{clientId}/items/{productId}")
    public ResponseEntity<Cart> updateQuantity(
            @PathVariable String clientId,
            @PathVariable String productId,
            @RequestParam Integer quantity) {
        return ResponseEntity.ok(cartService.updateItemQuantity(clientId, productId, quantity));
    }

    // DELETE /api/cart/{clientId}/items/{productId}
    @DeleteMapping("/{clientId}/items/{productId}")
    public ResponseEntity<Cart> removeItem(
            @PathVariable String clientId,
            @PathVariable String productId) {
        return ResponseEntity.ok(cartService.removeItem(clientId, productId));
    }

    // DELETE /api/cart/{clientId}
    @DeleteMapping("/{clientId}")
    public ResponseEntity<Void> clearCart(@PathVariable String clientId) {
        cartService.clearCart(clientId);
        return ResponseEntity.noContent().build();
    }
}