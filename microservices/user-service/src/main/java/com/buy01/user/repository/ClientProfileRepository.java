package com.buy01.user.repository;

import com.buy01.user.model.ClientProfile;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClientProfileRepository extends MongoRepository<ClientProfile, String> {
    Optional<ClientProfile> findByClientId(String clientId);
}