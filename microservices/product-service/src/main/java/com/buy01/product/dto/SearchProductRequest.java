package com.buy01.product.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SearchProductRequest {
    private String keyword;
    private String category;
    private Double minPrice;
    private Double maxPrice;
}