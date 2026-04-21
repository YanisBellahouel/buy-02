package com.buy01.order_service.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderItem {
    private String productId;
    private String productName;   // snapshot
    private Double productPrice;  // snapshot
    private String imageId;       // snapshot
    private Integer quantity;
}