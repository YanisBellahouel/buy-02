package com.buy01.user.service;

import com.buy01.user.model.ClientProfile;
import com.buy01.user.model.SellerProfile;
import com.buy01.user.repository.ClientProfileRepository;
import com.buy01.user.repository.SellerProfileRepository;
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
}