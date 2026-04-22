package com.buy01.cart_service.service;

import com.buy01.cart_service.model.Cart;
import com.buy01.cart_service.model.CartItem;
import com.buy01.cart_service.repository.CartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CartService {

    private final CartRepository cartRepository;

    // Récupérer le panier d'un client (le crée s'il n'existe pas)
    public Cart getOrCreateCart(String clientId) {
        return cartRepository.findByClientId(clientId)
                .orElseGet(() -> {
                    Cart newCart = new Cart();
                    newCart.setClientId(clientId);
                    newCart.setItems(new ArrayList<>());
                    newCart.setCreatedAt(LocalDateTime.now());
                    newCart.setUpdatedAt(LocalDateTime.now());
                    return cartRepository.save(newCart);
                });
    }

    // Ajouter ou mettre à jour un article dans le panier
    public Cart addItem(String clientId, CartItem newItem) {
        Cart cart = getOrCreateCart(clientId);
        List<CartItem> items = cart.getItems();

        // Si le produit existe déjà dans le panier, on augmente la quantité
        Optional<CartItem> existing = items.stream()
                .filter(i -> i.getProductId().equals(newItem.getProductId()))
                .findFirst();

        if (existing.isPresent()) {
            existing.get().setQuantity(existing.get().getQuantity() + newItem.getQuantity());
        } else {
            items.add(newItem);
        }

        cart.setUpdatedAt(LocalDateTime.now());
        return cartRepository.save(cart);
    }

    // Modifier la quantité d'un article
    public Cart updateItemQuantity(String clientId, String productId, Integer quantity) {
        Cart cart = getOrCreateCart(clientId);

        if (quantity <= 0) {
            return removeItem(clientId, productId);
        }

        cart.getItems().stream()
                .filter(i -> i.getProductId().equals(productId))
                .findFirst()
                .ifPresent(i -> i.setQuantity(quantity));

        cart.setUpdatedAt(LocalDateTime.now());
        return cartRepository.save(cart);
    }

    // Supprimer un article du panier
    public Cart removeItem(String clientId, String productId) {
        Cart cart = getOrCreateCart(clientId);
        cart.getItems().removeIf(i -> i.getProductId().equals(productId));
        cart.setUpdatedAt(LocalDateTime.now());
        return cartRepository.save(cart);
    }

    // Vider le panier
    public void clearCart(String clientId) {
        Cart cart = getOrCreateCart(clientId);
        cart.setItems(new ArrayList<>());
        cart.setUpdatedAt(LocalDateTime.now());
        cartRepository.save(cart);
    }
}