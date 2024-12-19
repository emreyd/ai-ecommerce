package com.bilgi.ai_ecommerce.order;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OrderService {

    private final OrderRepository orderRepository;

    @Autowired
    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public Order getOrderById(String orderId) {
        return orderRepository.findById(orderId)
                .orElseThrow(() -> new NoSuchElementException("Order with ID " + orderId + " not found"));
    }

    public List<Order> getOrdersForUser(String userId) {
        return orderRepository.findAll()
                .stream()
                .filter(order -> order.getBuyerId().equals(userId))
                .collect(Collectors.toList());
    }


    public Order createOrder(Order order) {
        order.setOrderedAt(LocalDateTime.now().toString());
        order.setUpdatedAt(LocalDateTime.now().toString());
        return orderRepository.save(order);
    }

    public Order updateOrderStatus(String orderId, String newStatus) {
        Optional<Order> optionalOrder = orderRepository.findById(orderId);
        if (optionalOrder.isPresent()) {
            Order order = optionalOrder.get();
            order.setStatus(newStatus);
            order.setUpdatedAt(LocalDateTime.now().toString());
            return orderRepository.save(order);
        } else {
            throw new NoSuchElementException("Order with ID " + orderId + " not found");
        }
    }

    public void deleteOrder(String orderId) {
        if (orderRepository.existsById(orderId)) {
            orderRepository.deleteById(orderId);
        } else {
            throw new NoSuchElementException("Order with ID " + orderId + " not found");
        }
    }
}
