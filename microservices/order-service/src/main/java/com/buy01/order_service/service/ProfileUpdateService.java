package com.buy01.order_service.service;

import com.buy01.order_service.dto.UpdateProfileRequest;
import com.buy01.order_service.model.Order;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProfileUpdateService {

    private final WebClient.Builder webClientBuilder;

    // Mettre à jour le profil client après livraison
    public void updateClientProfile(Order order) {
        try {
            UpdateProfileRequest request = new UpdateProfileRequest(
                order.getTotalAmount(),
                order.getItems().stream()
                    .map(item -> new UpdateProfileRequest.OrderItemSummary(
                        item.getProductId(),
                        item.getProductName(),
                        item.getQuantity()
                    ))
                    .collect(Collectors.toList())
            );

            webClientBuilder.build()
                .put()
                .uri("http://user-service/api/users/" + order.getClientId() + "/profile/client/update")
                .bodyValue(request)
                .retrieve()
                .bodyToMono(Void.class)
                .subscribe(
                    null,
                    error -> log.error("Failed to update client profile: {}", error.getMessage())
                );

            log.info("Client profile update requested for: {}", order.getClientId());
        } catch (Exception e) {
            log.error("Error updating client profile", e);
        }
    }

    // Mettre à jour le profil seller après livraison
    public void updateSellerProfile(Order order) {
        try {
            UpdateProfileRequest request = new UpdateProfileRequest(
                order.getTotalAmount(),
                order.getItems().stream()
                    .map(item -> new UpdateProfileRequest.OrderItemSummary(
                        item.getProductId(),
                        item.getProductName(),
                        item.getQuantity()
                    ))
                    .collect(Collectors.toList())
            );

            webClientBuilder.build()
                .put()
                .uri("http://user-service/api/users/" + order.getSellerId() + "/profile/seller/update")
                .bodyValue(request)
                .retrieve()
                .bodyToMono(Void.class)
                .subscribe(
                    null,
                    error -> log.error("Failed to update seller profile: {}", error.getMessage())
                );

            log.info("Seller profile update requested for: {}", order.getSellerId());
        } catch (Exception e) {
            log.error("Error updating seller profile", e);
        }
    }
}