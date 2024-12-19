package com.bilgi.ai_ecommerce.order;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface OrderRepository extends MongoRepository<Order, String> {
    // Additional custom queries if needed
}
