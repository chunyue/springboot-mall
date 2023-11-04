package com.chunyue.springbootmall.dao;

import com.chunyue.springbootmall.dto.CreateOrderRequest;
import com.chunyue.springbootmall.model.OrderItem;

import java.util.List;

public interface OrderDao {

    Integer createOrder(Integer userId, Integer amount);

    void createOrderItems(Integer orderId, List<OrderItem> list);
}
