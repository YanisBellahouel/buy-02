package com.buy01.cart_service.service;

import com.buy01.cart_service.dto.CheckoutRequest;
import com.buy01.cart_service.dto.CheckoutResponse;
import com.buy01.cart_service.model.Cart;
import com.buy01.cart_service.model.CartItem;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CheckoutService {

    private final CartService cartService;
    private final WebClient.Builder webClientBuilder;

    public CheckoutResponse checkout(String clientId, CheckoutRequest request) {

        // 1. Récupérer le panier
        Cart cart = cartService.getOrCreateCart(clientId);

        // 2. Vérifier que le panier n'est pas vide
        if (cart.getItems() == null || cart.getItems().isEmpty()) {
            throw new RuntimeException("Cart is empty");
        }

        // 3. Grouper les articles par sellerId
        Map<String, List<CartItem>> itemsBySeller = cart.getItems().stream()
                .collect(Collectors.groupingBy(CartItem::getSellerId));

        // 4. Créer une commande par seller
        List<String> orderIds = new ArrayList<>();

        for (Map.Entry<String, List<CartItem>> entry : itemsBySeller.entrySet()) {
            String sellerId = entry.getKey();
            List<CartItem> sellerItems = entry.getValue();

            // Calculer le total pour ce seller
            double total = sellerItems.stream()
                    .mapToDouble(i -> i.getProductPrice() * i.getQuantity())
                    .sum();

            // Construire le body de la commande
            Map<String, Object> orderBody = new HashMap<>();
            orderBody.put("clientId", clientId);
            orderBody.put("sellerId", sellerId);
            orderBody.put("totalAmount", total);
            orderBody.put("paymentMethod", "PAY_ON_DELIVERY");
            orderBody.put("shippingAddress", Map.of(
                    "street", request.getStreet(),
                    "city", request.getCity(),
                    "postalCode", request.getPostalCode(),
                    "country", request.getCountry()
            ));
            orderBody.put("items", sellerItems.stream().map(item -> Map.of(
                    "productId", item.getProductId(),
                    "productName", item.getProductName(),
                    "productPrice", item.getProductPrice(),
                    "imageId", item.getImageId() != null ? item.getImageId() : "",
                    "quantity", item.getQuantity()
            )).collect(Collectors.toList()));

            // 5. Appeler order-service via WebClient
            Map response = webClientBuilder.build()
                    .post()
                    .uri("http://order-service/api/orders")
                    .bodyValue(orderBody)
                    .retrieve()
                    .bodyToMono(Map.class)
                    .block();

            if (response != null && response.get("id") != null) {
                orderIds.add(response.get("id").toString());
            }
        }

        // 6. Vider le panier après checkout
        cartService.clearCart(clientId);

        return new CheckoutResponse(orderIds, "Checkout successful. " + orderIds.size() + " order(s) created.");
    }
}
