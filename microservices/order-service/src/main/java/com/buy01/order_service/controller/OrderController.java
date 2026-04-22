package com.buy01.order_service.controller;

import com.buy01.order_service.model.Order;
import com.buy01.order_service.model.OrderStatus;
import com.buy01.order_service.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    // POST /api/orders
    @PostMapping
    public ResponseEntity<Order> createOrder(@RequestBody Order order) {
        return ResponseEntity.ok(orderService.createOrder(order));
    }

    // GET /api/orders/{orderId}
    @GetMapping("/{orderId}")
    public ResponseEntity<Order> getOrder(@PathVariable String orderId) {
        return ResponseEntity.ok(orderService.getOrderById(orderId));
    }

    // GET /api/orders/client/{clientId}
    @GetMapping("/client/{clientId}")
    public ResponseEntity<List<Order>> getOrdersByClient(
            @PathVariable String clientId,
            @RequestParam(required = false) OrderStatus status) {

        if (status != null) {
            return ResponseEntity.ok(orderService.getOrdersByClientIdAndStatus(clientId, status));
        }
        return ResponseEntity.ok(orderService.getOrdersByClientId(clientId));
    }

    // GET /api/orders/seller/{sellerId}
    @GetMapping("/seller/{sellerId}")
    public ResponseEntity<List<Order>> getOrdersBySeller(
            @PathVariable String sellerId,
            @RequestParam(required = false) OrderStatus status) {

        if (status != null) {
            return ResponseEntity.ok(orderService.getOrdersBySellerIdAndStatus(sellerId, status));
        }
        return ResponseEntity.ok(orderService.getOrdersBySellerId(sellerId));
    }

    // PUT /api/orders/{orderId}/status
    @PutMapping("/{orderId}/status")
    public ResponseEntity<Order> updateStatus(
            @PathVariable String orderId,
            @RequestParam OrderStatus status) {
        return ResponseEntity.ok(orderService.updateStatus(orderId, status));
    }

    // PUT /api/orders/{orderId}/cancel
    @PutMapping("/{orderId}/cancel")
    public ResponseEntity<Order> cancelOrder(@PathVariable String orderId) {
        return ResponseEntity.ok(orderService.cancelOrder(orderId));
    }

    // DELETE /api/orders/{orderId}
    @DeleteMapping("/{orderId}")
    public ResponseEntity<Void> deleteOrder(@PathVariable String orderId) {
        orderService.deleteOrder(orderId);
        return ResponseEntity.noContent().build();
    }
}