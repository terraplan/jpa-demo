package com.terraplan.demo.jpa;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
public class Customer {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    @ManyToMany(mappedBy="customers")
    private Set<Order> orders = new HashSet<>();

    public void addOrder(Order order) {
        if (!orders.contains(order)) {
            orders.add(order);
        }
    }

    public void removeOrder(Order order) {
        orders.remove(order);
    }
}
