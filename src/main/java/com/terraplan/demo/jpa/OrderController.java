package com.terraplan.demo.jpa;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/orders")
public class OrderController {

    private final OrderService service;

    @GetMapping(value = "/{id}")
    public ResponseEntity<OrderDto> findOrder(@PathVariable Long id) {

        Optional<Order> order = service.findOrder(id);
        return order.map(value -> ResponseEntity.ok().body(OrderDto.fromOrder(value))).orElseGet(() -> ResponseEntity.notFound().build());

    }

    @PostMapping(consumes = {"application/json"})
    public ResponseEntity<OrderDto> createOrder(@RequestBody OrderDto orderDto) {

        Order order = orderDto.toOrder();
        order = service.createOrder(order);

        return ResponseEntity.status(HttpStatus.CREATED).body(OrderDto.fromOrder(order));
    }

    @PutMapping(value = "/{id}", consumes = {"application/json"})
    public ResponseEntity<OrderDto> updateOrder(@PathVariable Long id, @RequestBody OrderDto orderDto) {

        Order order = orderDto.toOrder();
        order.setId(id); // should not be needed ...

        order = service.updateOrder(order);

        return ResponseEntity.status(HttpStatus.OK).body(OrderDto.fromOrder(order));
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity deleteOrder(@PathVariable Long id) {

        service.deleteOrder(id);
        return ResponseEntity.ok().build();
    }
}