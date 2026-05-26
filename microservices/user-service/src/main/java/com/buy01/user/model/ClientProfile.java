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
@Document(collection = "client_profiles")
public class ClientProfile {

    @Id
    private String id;

    @Indexed(unique = true)
    private String clientId;  // ref users._id (role == CLIENT)

    private Double totalSpent = 0.0;
    private Integer totalOrders = 0;
    private List<BoughtProductSummary> mostBoughtProducts = new ArrayList<>();

    private LocalDateTime updatedAt;
}