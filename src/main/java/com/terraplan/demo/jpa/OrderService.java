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
    private final DayEntryRepository dayEntryRepository;

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
        // remove the order from all customers that are in the originalOrders customers but not in the new one
        // simple solution: delete all and add them again ...
        originalOrder.getCustomers().forEach(customer -> customer.removeOrder(order));

        originalOrder.setName(order.getName());

        originalOrder.updateOrderItems(order.getOrderItems());

        originalOrder.setComments(order.getComments());

        originalOrder.setPlace(order.getPlace());

        originalOrder.setSharedComments(order.getSharedComments());

        originalOrder.setCustomers(order.getCustomers());
        // for each customer adapt the order in the set of orders
        originalOrder.getCustomers().forEach(customer -> customer.addOrder(order));

        // remove the orphaned DayEntries from the database (this is not done automatically)
        originalOrder.getDays().forEach((key, value) -> {
            DayEntry dayEntry = order.getDays().get(key);
            if (null == dayEntry || !dayEntry.getId().equals(value.getId())) {
                dayEntryRepository.deleteById(value.getId());
            }
        });
        originalOrder.updateDays(order.getDays());

        return repository.save(originalOrder);
    }

    public void deleteOrder(Long id) {
        repository.deleteById(id);
    }
}
