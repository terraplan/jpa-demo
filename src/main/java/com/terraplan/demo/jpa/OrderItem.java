package com.terraplan.demo.jpa;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Getter
@Setter
@Entity
public class OrderItem {
    @Id
    @GeneratedValue
    private Long id;

    private String name;

    @ManyToOne
    private Order order;
}
