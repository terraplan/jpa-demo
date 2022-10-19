package com.terraplan.demo.jpa;

import lombok.Data;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Data
public class OrderDto {

    private String id;
    private String name;
    private List<OrderItemDto> orderItems = new ArrayList<>();
    private String placeId;
    private List<String> comments = new ArrayList<>();
    private Set<String> sharedComments = new HashSet<>();

    public static OrderDto fromOrder(Order order) {
        OrderDto orderDto = new OrderDto();
        orderDto.setId(order.getId() != null ? order.getId().toString() : null);
        orderDto.setName(order.getName());
        orderDto.setOrderItems(order.getOrderItems().stream().map(OrderItemDto::fromOrderItem).toList());
        orderDto.setPlaceId(null != order.getPlace() ? order.getPlace().getId().toString() : null);
        orderDto.setComments(order.getComments().stream().map(comment -> comment.getId().toString()).toList());
        orderDto.setSharedComments(order.getSharedComments().stream().map(comment -> comment.getId().toString()).collect(Collectors.toSet()));
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
