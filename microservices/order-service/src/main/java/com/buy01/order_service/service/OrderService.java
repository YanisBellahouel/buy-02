package com.buy01.order_service.service;


import com.buy01.order_service.model.Order;
import com.buy01.order_service.model.OrderStatus;
import com.buy01.order_service.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;

    // Créer une commande (appelé depuis le checkout du cart)
    public Order createOrder(Order order) {
        order.setStatus(OrderStatus.PENDING);
        order.setCreatedAt(LocalDateTime.now());
        order.setUpdatedAt(LocalDateTime.now());
        return orderRepository.save(order);
    }

    // Récupérer une commande par ID
    public Order getOrderById(String orderId) {
        return orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found: " + orderId));
    }

    // Récupérer toutes les commandes d'un client
    public List<Order> getOrdersByClientId(String clientId) {
        return orderRepository.findByClientId(clientId);
    }

    // Récupérer toutes les commandes d'un seller
    public List<Order> getOrdersBySellerId(String sellerId) {
        return orderRepository.findBySellerId(sellerId);
    }

    // Récupérer les commandes d'un client par statut
    public List<Order> getOrdersByClientIdAndStatus(String clientId, OrderStatus status) {
        return orderRepository.findByClientIdAndStatus(clientId, status);
    }

    // Récupérer les commandes d'un seller par statut
    public List<Order> getOrdersBySellerIdAndStatus(String sellerId, OrderStatus status) {
        return orderRepository.findBySellerIdAndStatus(sellerId, status);
    }

    // Mettre à jour le statut d'une commande
    public Order updateStatus(String orderId, OrderStatus newStatus) {
        Order order = getOrderById(orderId);
        order.setStatus(newStatus);
        order.setUpdatedAt(LocalDateTime.now());
        return orderRepository.save(order);
    }

    // Annuler une commande
    public Order cancelOrder(String orderId) {
        Order order = getOrderById(orderId);

        if (order.getStatus() == OrderStatus.DELIVERED) {
            throw new RuntimeException("Cannot cancel a delivered order");
        }

        order.setStatus(OrderStatus.CANCELLED);
        order.setUpdatedAt(LocalDateTime.now());
        return orderRepository.save(order);
    }

    // Supprimer une commande (seulement si CANCELLED)
    public void deleteOrder(String orderId) {
        Order order = getOrderById(orderId);

        if (order.getStatus() != OrderStatus.CANCELLED) {
            throw new RuntimeException("Only cancelled orders can be deleted");
        }

        orderRepository.deleteById(orderId);
    }
}
