package com.buy01.cart_service.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartItem {
    private String productId;
    private String productName;   // snapshot
    private Double productPrice;  // snapshot
    private String imageId;       // snapshot
    private Integer quantity;
}