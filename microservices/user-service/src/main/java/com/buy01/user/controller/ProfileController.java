package com.buy01.user.controller;

import com.buy01.user.model.ClientProfile;
import com.buy01.user.model.SellerProfile;
import com.buy01.user.service.ProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class ProfileController {

    private final ProfileService profileService;

    // GET /api/users/{clientId}/profile/client
    @GetMapping("/{clientId}/profile/client")
    public ResponseEntity<ClientProfile> getClientProfile(@PathVariable String clientId) {
        return ResponseEntity.ok(profileService.getClientProfile(clientId));
    }

    // GET /api/users/{sellerId}/profile/seller
    @GetMapping("/{sellerId}/profile/seller")
    public ResponseEntity<SellerProfile> getSellerProfile(@PathVariable String sellerId) {
        return ResponseEntity.ok(profileService.getSellerProfile(sellerId));
    }
}