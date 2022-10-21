package com.terraplan.demo.jpa;

import lombok.Data;

import java.time.DayOfWeek;
import java.util.*;
import java.util.stream.Collectors;

@Data
public class OrderDto {

    private String id;
    private String name;
    private List<OrderItemDto> orderItems = new ArrayList<>();
    private String placeId;
    private List<String> comments = new ArrayList<>();
    private Set<String> sharedComments = new HashSet<>();
    private Set<String> customers = new HashSet<>();
    private Map<DayOfWeek, DayEntryDto> days = new EnumMap<>(DayOfWeek.class);

    public static OrderDto fromOrder(Order order) {
        OrderDto orderDto = new OrderDto();
        orderDto.setId(order.getId() != null ? order.getId().toString() : null);
        orderDto.setName(order.getName());
        orderDto.setOrderItems(order.getOrderItems().stream().map(OrderItemDto::fromOrderItem).toList());
        orderDto.setPlaceId(null != order.getPlace() ? order.getPlace().getId().toString() : null);
        orderDto.setComments(order.getComments().stream().map(comment -> comment.getId().toString()).toList());
        orderDto.setSharedComments(order.getSharedComments().stream().map(comment -> comment.getId().toString()).collect(Collectors.toSet()));
        orderDto.setCustomers(order.getCustomers().stream().map(customer -> customer.getId().toString()).collect(Collectors.toSet()));
        for (DayOfWeek dayOfWeek : order.getDays().keySet()) {
            DayEntry dayEntry = order.getDays().get(dayOfWeek);
            orderDto.days.put(dayOfWeek, new DayEntryDto(dayEntry.getId().toString(), dayEntry.getName()));
        }
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
