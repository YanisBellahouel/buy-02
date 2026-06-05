package com.buy01.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateProfileRequest {
    private Double totalAmount;
    private List<OrderItemSummary> items;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class OrderItemSummary {
        private String productId;
        private String productName;
        private Integer quantity;
    }
}