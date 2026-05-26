package com.buy01.user.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SoldProductSummary {
    private String productId;
    private String productName;
    private Integer totalQuantitySold;
}
