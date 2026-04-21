package com.buy01.cart_service.repository;

import com.buy01.cart_service.model.Cart;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartRepository extends MongoRepository<Cart, String> {
    Optional<Cart> findByClientId(String clientId);
    void deleteByClientId(String clientId);
}