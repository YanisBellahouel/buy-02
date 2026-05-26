package com.buy01.user.repository;

import com.buy01.user.model.SellerProfile;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SellerProfileRepository extends MongoRepository<SellerProfile, String> {
    Optional<SellerProfile> findBySellerId(String sellerId);
}