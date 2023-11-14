package com.chunyue.springbootmall.controller;

import com.chunyue.springbootmall.dto.CreateOrderRequest;
import com.chunyue.springbootmall.dto.QueryParams;
import com.chunyue.springbootmall.model.Order;
import com.chunyue.springbootmall.service.OrderService;
import com.chunyue.springbootmall.util.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.List;

@Validated
@RestController
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/users/{userId}/orders")
    public ResponseEntity<Order> createOrder(@PathVariable Integer userId,
                                        @RequestBody @Valid CreateOrderRequest createOrderRequest){

        Integer orderId = orderService.createOrder(userId, createOrderRequest);

        Order order = orderService.getOrderById(orderId);

        return ResponseEntity.status(HttpStatus.CREATED).body(order);
    }

    @GetMapping("/users/{userId}/orders")
    public ResponseEntity<Page<Order>> getOrders(@PathVariable Integer userId,
                                                 @RequestParam (defaultValue = "5") @Max(1000) @Min(0) Integer limit,
                                                 @RequestParam (defaultValue = "0") @Min(0) Integer offset){
        QueryParams queryParams = QueryParams.builder().limit(limit).offset(offset).build();

        List<Order> orderList = orderService.getOrdersByUserId(userId, queryParams);

        Integer orderCount = orderService.countOrders(userId, queryParams);

        Page<Order> page = new Page<>();
        page.setLimit(limit);
        page.setOffset(offset);
        page.setTotal(orderCount);
        page.setResults(orderList);


        return ResponseEntity.status(HttpStatus.OK).body(page);
    }
}
