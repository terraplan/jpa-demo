package com.terraplan.demo.jpa;

import lombok.Data;

@Data
public class OrderDto {
    private String id;
    private String name;

    public static OrderDto fromOrder(Order order) {
        OrderDto orderDto = new OrderDto();
        orderDto.setId(order.getId() != null ? order.getId().toString() : null);
        orderDto.setName(order.getName());
        return orderDto;
    }

    public Order toOrder() {
        Order order = new Order();
        order.setId(this.id != null ? Long.valueOf(this.id) : null);
        order.setName(this.name);
        return order;
    }
}
