package com.buy01.user.controller;

import com.buy01.user.model.ClientProfile;
import com.buy01.user.model.SellerProfile;
import com.buy01.user.service.ProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.buy01.user.dto.UpdateProfileRequest;

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


// PUT /api/users/{clientId}/profile/client/update
@PutMapping("/{clientId}/profile/client/update")
public ResponseEntity<ClientProfile> updateClientProfile(
        @PathVariable String clientId,
        @RequestBody UpdateProfileRequest request) {
    return ResponseEntity.ok(profileService.updateClientProfile(clientId, request));
}

// PUT /api/users/{sellerId}/profile/seller/update
@PutMapping("/{sellerId}/profile/seller/update")
public ResponseEntity<SellerProfile> updateSellerProfile(
        @PathVariable String sellerId,
        @RequestBody UpdateProfileRequest request) {
    return ResponseEntity.ok(profileService.updateSellerProfile(sellerId, request));
}
}