package com.terraplan.demo.jpa;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class OrderDto {

    private String id;
    private String name;
    private List<OrderItemDto> orderItems = new ArrayList<>();

    public static OrderDto fromOrder(Order order) {
        OrderDto orderDto = new OrderDto();
        orderDto.setId(order.getId() != null ? order.getId().toString() : null);
        orderDto.setName(order.getName());
        orderDto.setOrderItems(order.getOrderItems().stream().map(OrderItemDto::fromOrderItem).toList());
        return orderDto;
    }

    public Order toOrder() {
        Order order = new Order();
        order.setId(this.id != null ? Long.valueOf(this.id) : null);
        order.setName(this.name);
        order.setOrderItems(orderItems.stream().map(OrderItemDto::toOrderItem).toList());
        return order;
    }
}
