package com.buy01.order_service.repository;

import com.buy01.order_service.model.Order;
import com.buy01.order_service.model.OrderStatus;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends MongoRepository<Order, String> {

    // Récupérer toutes les commandes d'un client
    List<Order> findByClientId(String clientId);

    // Récupérer toutes les commandes d'un seller
    List<Order> findBySellerId(String sellerId);

    // Récupérer les commandes par statut
    List<Order> findByClientIdAndStatus(String clientId, OrderStatus status);
    List<Order> findBySellerIdAndStatus(String sellerId, OrderStatus status);
}
