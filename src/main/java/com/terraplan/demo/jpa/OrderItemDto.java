package com.terraplan.demo.jpa;

import lombok.Data;

@Data
public class OrderItemDto {
    private String id;
    private String name;

    public static OrderItemDto fromOrderItem(OrderItem orderItem) {
        OrderItemDto orderItemDto = new OrderItemDto();
        orderItemDto.setId(orderItem.getId() != null ? orderItem.getId().toString() : null);
        orderItemDto.setName(orderItem.getName());
        return orderItemDto;
    }

    public OrderItem toOrderItem() {
        OrderItem orderItem = new OrderItem();
        orderItem.setId(this.id != null ? Long.valueOf(this.id) : null);
        orderItem.setName(this.name);
        return orderItem;
    }
}
