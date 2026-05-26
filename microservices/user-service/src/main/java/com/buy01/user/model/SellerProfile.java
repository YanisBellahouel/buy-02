package com.buy01.user.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.index.Indexed;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "seller_profiles")
public class SellerProfile {

    @Id
    private String id;

    @Indexed(unique = true)
    private String sellerId;  // ref users._id (role == SELLER)

    private Double totalRevenue = 0.0;
    private Integer totalSales = 0;
    private List<SoldProductSummary> bestSellingProducts = new ArrayList<>();

    private LocalDateTime updatedAt;
}