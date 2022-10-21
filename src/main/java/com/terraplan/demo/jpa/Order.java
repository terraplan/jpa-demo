package com.terraplan.demo.jpa;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.DayOfWeek;
import java.util.*;

@Getter
@Setter
@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    // bidirectional one2many
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItem> orderItems = new ArrayList<>();

    // unidirectional one2many
    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<Comment> comments = new ArrayList<>();

    // place example: place also be used for other Order objects
    // orphanRemoval = true:: depends on whether we want to keep places ...
    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Place place;

    // unidirectional many2many
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Set<Comment> sharedComments = new HashSet<>();

    // bidirectional many2many (although multiple customers per order do not make much sense ...)
    @ManyToMany
    private Set<Customer> customers = new HashSet<>();

    // map example
    @ManyToMany(cascade = {CascadeType.ALL})
    // orphanRemoval is not supported, therefor we have to clean up by ourselves
    @MapKey(name = "day")
    @MapKeyEnumerated(EnumType.STRING)
    private Map<DayOfWeek, DayEntry> days = new EnumMap<>(DayOfWeek.class);

    public void updateOrderItems(List<OrderItem> orderItems) {
        this.orderItems.clear();
        this.orderItems.addAll(orderItems);
        this.orderItems.forEach(orderItem -> orderItem.setOrder(this));
    }

    public void updateDays(Map<DayOfWeek, DayEntry> days) {
        this.days.clear();
        this.days.putAll(days);
    }
}
