package com.buy01.user.service;

import com.buy01.user.model.BoughtProductSummary;
import com.buy01.user.model.ClientProfile;
import com.buy01.user.model.SellerProfile;
import com.buy01.user.model.SoldProductSummary;
import com.buy01.user.repository.ClientProfileRepository;
import com.buy01.user.repository.SellerProfileRepository;
import com.buy01.user.dto.UpdateProfileRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ProfileService {

    private final ClientProfileRepository clientProfileRepository;
    private final SellerProfileRepository sellerProfileRepository;

    // Créer un profil client vide à l'inscription
    public ClientProfile createClientProfile(String clientId) {
        ClientProfile profile = new ClientProfile();
        profile.setClientId(clientId);
        profile.setTotalSpent(0.0);
        profile.setTotalOrders(0);
        profile.setUpdatedAt(LocalDateTime.now());
        return clientProfileRepository.save(profile);
    }

    // Créer un profil seller vide à l'inscription
    public SellerProfile createSellerProfile(String sellerId) {
        SellerProfile profile = new SellerProfile();
        profile.setSellerId(sellerId);
        profile.setTotalRevenue(0.0);
        profile.setTotalSales(0);
        profile.setUpdatedAt(LocalDateTime.now());
        return sellerProfileRepository.save(profile);
    }

    // Récupérer le profil client
    public ClientProfile getClientProfile(String clientId) {
        return clientProfileRepository.findByClientId(clientId)
                .orElseThrow(() -> new RuntimeException("Client profile not found: " + clientId));
    }

    // Récupérer le profil seller
    public SellerProfile getSellerProfile(String sellerId) {
        return sellerProfileRepository.findBySellerId(sellerId)
                .orElseThrow(() -> new RuntimeException("Seller profile not found: " + sellerId));
    }

// Mettre à jour le profil client après livraison
public ClientProfile updateClientProfile(String clientId, UpdateProfileRequest request) {
    ClientProfile profile = clientProfileRepository.findByClientId(clientId)
            .orElseThrow(() -> new RuntimeException("Client profile not found: " + clientId));

    // Mettre à jour le total dépensé et le nombre de commandes
    profile.setTotalSpent(profile.getTotalSpent() + request.getTotalAmount());
    profile.setTotalOrders(profile.getTotalOrders() + 1);

    // Mettre à jour les produits les plus achetés
    for (UpdateProfileRequest.OrderItemSummary item : request.getItems()) {
        profile.getMostBoughtProducts().stream()
            .filter(p -> p.getProductId().equals(item.getProductId()))
            .findFirst()
            .ifPresentOrElse(
                existing -> existing.setTotalQuantityBought(
                    existing.getTotalQuantityBought() + item.getQuantity()),
                () -> profile.getMostBoughtProducts().add(
                    new BoughtProductSummary(
                        item.getProductId(),
                        item.getProductName(),
                        item.getQuantity()
                    ))
            );
    }

    profile.setUpdatedAt(LocalDateTime.now());
    return clientProfileRepository.save(profile);
}

// Mettre à jour le profil seller après livraison
public SellerProfile updateSellerProfile(String sellerId, UpdateProfileRequest request) {
    SellerProfile profile = sellerProfileRepository.findBySellerId(sellerId)
            .orElseThrow(() -> new RuntimeException("Seller profile not found: " + sellerId));

    // Mettre à jour le revenu total et le nombre de ventes
    profile.setTotalRevenue(profile.getTotalRevenue() + request.getTotalAmount());
    profile.setTotalSales(profile.getTotalSales() + 1);

    // Mettre à jour les produits les plus vendus
    for (UpdateProfileRequest.OrderItemSummary item : request.getItems()) {
        profile.getBestSellingProducts().stream()
            .filter(p -> p.getProductId().equals(item.getProductId()))
            .findFirst()
            .ifPresentOrElse(
                existing -> existing.setTotalQuantitySold(
                    existing.getTotalQuantitySold() + item.getQuantity()),
                () -> profile.getBestSellingProducts().add(
                    new SoldProductSummary(
                        item.getProductId(),
                        item.getProductName(),
                        item.getQuantity()
                    ))
            );
    }

    profile.setUpdatedAt(LocalDateTime.now());
    return sellerProfileRepository.save(profile);
}
}