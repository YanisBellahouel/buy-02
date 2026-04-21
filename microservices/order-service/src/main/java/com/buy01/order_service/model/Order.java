package com.buy01.order_service.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "orders")
public class Order {

    @Id
    private String id;

    private String clientId;
    private String sellerId;

    private OrderStatus status = OrderStatus.PENDING;
    private PaymentMethod paymentMethod = PaymentMethod.PAY_ON_DELIVERY;

    private List<OrderItem> items = new ArrayList<>();

    private Double totalAmount;

    private ShippingAddress shippingAddress;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}