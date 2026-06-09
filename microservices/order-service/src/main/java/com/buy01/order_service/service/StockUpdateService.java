package com.buy01.order_service.service;

import com.buy01.order_service.model.Order;
import com.buy01.order_service.model.OrderItem;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
@RequiredArgsConstructor
@Slf4j
public class StockUpdateService {

    private final WebClient.Builder webClientBuilder;

    public void decrementStock(Order order) {
        for (OrderItem item : order.getItems()) {
            try {
                webClientBuilder.build()
                    .put()
                    .uri("http://product-service/api/products/" + item.getProductId() + "/stock"
                            + "?quantity=" + item.getQuantity())
                    .retrieve()
                    .bodyToMono(Void.class)
                    .subscribe(
                        null,
                        error -> log.error("Failed to update stock for product {}: {}",
                                item.getProductId(), error.getMessage())
                    );

                log.info("Stock update requested for product: {}", item.getProductId());
            } catch (Exception e) {
                log.error("Error updating stock for product: {}", item.getProductId(), e);
            }
        }
    }
}