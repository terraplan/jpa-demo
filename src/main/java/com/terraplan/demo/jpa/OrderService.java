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
        order.getCustomers().forEach(customer -> customer.addOrder(order));
        return savedOrder;
    }

    public Order updateOrder(Order order) {

        Order originalOrder = repository.findById(order.getId()).orElseThrow();

        order.getOrderItems().forEach(orderItem -> orderItem.setOrder(order));

        // remove the order from all customers that are in the originalOrders customers but not in the new one
        // simple solution: delete all and add them again ...
        originalOrder.getCustomers().forEach(customer -> customer.removeOrder(order));

        // for each customer adapt the order in the set of orders
        order.getCustomers().forEach(customer -> customer.addOrder(order));

        // !! we save the provided order, not the original one loaded from the repository!
        return repository.save(order);
    }

    public void deleteOrder(Long id) {
        repository.deleteById(id);
    }
}
