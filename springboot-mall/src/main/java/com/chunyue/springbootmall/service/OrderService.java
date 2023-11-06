package com.chunyue.springbootmall.service;

import com.chunyue.springbootmall.dto.CreateOrderRequest;
import com.chunyue.springbootmall.model.Order;

import java.util.List;

public interface OrderService {

    Integer createOrder(Integer userId, CreateOrderRequest createOrderRequest);

    Order getOrderById(Integer orderId);

}
