package com.terraplan.demo.jpa;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Transactional
@RequiredArgsConstructor
@Service
public class OrderService {

    private final OrderRepository repository;

    public Optional<Order> findOrder(Long id) {
        return repository.findById(id);
    }

    public Order createOrder(Order order) {
        Order savedOrder = repository.save(order);
        order.getOrderItems().forEach(orderItem -> orderItem.setOrder(savedOrder));
        return savedOrder;
    }

    public Order updateOrder(Order order) {
        order.getOrderItems().forEach(orderItem -> orderItem.setOrder(order));
        return repository.save(order);
    }

    public void deleteOrder(Long id) {
        repository.deleteById(id);
    }
}
