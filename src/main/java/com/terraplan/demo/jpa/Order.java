package com.terraplan.demo.jpa;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

    // bidirectional many2many

    // map example
}
