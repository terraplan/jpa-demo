package com.terraplan.demo.jpa;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class OrderService {

    private final OrderRepository repository;

    public Optional<Order> findOrder(Long id) {
        return repository.findById(id);
    }

    public Order createOrder(Order order) {
        return repository.save(order);
    }

    public Order updateOrder(Order order) {
        return repository.save(order);
    }

    public void deleteOrder(Long id) {
        repository.deleteById(id);
    }
}
