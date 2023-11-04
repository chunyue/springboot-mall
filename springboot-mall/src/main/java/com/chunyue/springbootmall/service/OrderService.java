package com.chunyue.springbootmall.service;

import com.chunyue.springbootmall.dto.CreateOrderRequest;

public interface OrderService {

    Integer createOrder(Integer userId, CreateOrderRequest createOrderRequest);


}
