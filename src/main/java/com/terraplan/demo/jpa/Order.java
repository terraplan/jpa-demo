package com.terraplan.demo.jpa;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

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
//    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
//    private List<Place> places = new ArrayList<>();

    // place example
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    private Place place;

    // bidirectional many2many

    // map example
}
