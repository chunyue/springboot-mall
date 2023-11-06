package com.chunyue.springbootmall.dao;

import com.chunyue.springbootmall.model.Order;
import com.chunyue.springbootmall.model.OrderItem;

import java.util.List;

public interface OrderDao {

    Integer createOrder(Integer userId, Integer amount);

    void createOrderItems(Integer orderId, List<OrderItem> list);

    Order getOrderById(Integer orderId);

    List<OrderItem> getOrderItemsByOrderId(Integer orderId);
}
