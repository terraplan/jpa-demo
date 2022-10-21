package com.terraplan.demo.jpa;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.DayOfWeek;
import java.util.*;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/orders")
public class OrderController {

    private final OrderService service;

    // FIXME this is actually not allowed here => use a service ... or move someplace else
    private final PlaceRepository placeRepository;
    private final CommentRepository commentRepository;
    private final CustomerRepository customerRepository;
    private final DayEntryRepository dayEntryRepository;

    @GetMapping(value = "/{id}")
    public ResponseEntity<OrderDto> findOrder(@PathVariable Long id) {

        Optional<Order> order = service.findOrder(id);
        return order.map(value -> ResponseEntity.ok().body(OrderDto.fromOrder(value))).orElseGet(() -> ResponseEntity.notFound().build());

    }

    @PostMapping(consumes = {"application/json"})
    public ResponseEntity<OrderDto> createOrder(@RequestBody OrderDto orderDto) {

        Order order = orderDto.toOrder();
        // FIXME handle place: retrieve the place from repository
        if (null != orderDto.getPlaceId()) {
            Place place = placeRepository.findById(Long.valueOf(orderDto.getPlaceId())).orElseThrow();
            order.setPlace(place);
        }
        // FIXME handle comments
        List<Comment> comments = new ArrayList<>();
        for (String commentId : orderDto.getComments()) {
            comments.add(commentRepository.findById(Long.valueOf(commentId)).orElseThrow());
        }
        order.setComments(comments);

        Set<Comment> sharedComments = new HashSet<>();
        for (String commentId : orderDto.getSharedComments()) {
            sharedComments.add(commentRepository.findById(Long.valueOf(commentId)).orElseThrow());
        }
        order.setSharedComments(sharedComments);

        Set<Customer> customers = new HashSet<>();
        for (String customerId : orderDto.getCustomers()) {
            customers.add(customerRepository.findById(Long.valueOf(customerId)).orElseThrow());
        }
        order.setCustomers(customers);

        // handle the map
        Map<DayOfWeek, DayEntry> days = new EnumMap<>(DayOfWeek.class);
        days.put(DayOfWeek.MONDAY, DayEntry.builder().day(DayOfWeek.MONDAY).name("Monday").build());
        days.put(DayOfWeek.TUESDAY, DayEntry.builder().day(DayOfWeek.TUESDAY).name("TUESDAY").build());
        order.setDays(days);

        order = service.createOrder(order);

        return ResponseEntity.status(HttpStatus.CREATED).body(OrderDto.fromOrder(order));
    }

    @PutMapping(value = "/{id}", consumes = {"application/json"})
    public ResponseEntity<OrderDto> updateOrder(@PathVariable Long id, @RequestBody OrderDto orderDto) {

        Order order = orderDto.toOrder();
        order.setId(id); // should not be needed ...
        // FIXME handle place: retrieve the place from repository
        if (null != orderDto.getPlaceId()) {
            Place place = placeRepository.findById(Long.valueOf(orderDto.getPlaceId())).orElseThrow();
            order.setPlace(place);
        }
        // FIXME handle comments
        List<Comment> comments = new ArrayList<>();
        for (String commentId : orderDto.getComments()) {
            comments.add(commentRepository.findById(Long.valueOf(commentId)).orElseThrow());
        }
        order.setComments(comments);

        Set<Comment> sharedComments = new HashSet<>();
        for (String commentId : orderDto.getSharedComments()) {
            sharedComments.add(commentRepository.findById(Long.valueOf(commentId)).orElseThrow());
        }
        order.setSharedComments(sharedComments);

        Set<Customer> customers = new HashSet<>();
        for (String customerId : orderDto.getCustomers()) {
            customers.add(customerRepository.findById(Long.valueOf(customerId)).orElseThrow());
        }
        order.setCustomers(customers);

        // handle the map
        Map<DayOfWeek, DayEntry> days = new EnumMap<>(DayOfWeek.class);
        for (DayOfWeek dayOfWeek : orderDto.getDays().keySet()) {
            DayEntryDto dayEntryDto = orderDto.getDays().get(dayOfWeek);
            days.put(dayOfWeek, DayEntry.builder()
                    .name(dayEntryDto.getName())
                    .day(dayOfWeek)
                    .id(null != dayEntryDto.getId() ? Long.valueOf(dayEntryDto.getId()) : null)
                    .build());
        }
        order.setDays(days);

        order = service.updateOrder(order);

        return ResponseEntity.status(HttpStatus.OK).body(OrderDto.fromOrder(order));
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity deleteOrder(@PathVariable Long id) {

        service.deleteOrder(id);
        return ResponseEntity.ok().build();
    }
}
