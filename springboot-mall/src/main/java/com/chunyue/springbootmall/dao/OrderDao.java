package com.chunyue.springbootmall.dao;

import com.chunyue.springbootmall.dto.QueryParams;
import com.chunyue.springbootmall.model.Order;
import com.chunyue.springbootmall.model.OrderItem;

import java.math.BigDecimal;
import java.util.List;

public interface OrderDao {

    Integer createOrder(Integer userId, BigDecimal amount);

    void createOrderItems(Integer orderId, List<OrderItem> list);

    Order getOrderById(Integer orderId);

    List<OrderItem> getOrderItemsByOrderId(Integer orderId);

    List<Order> getOrdersByUserId(Integer userId, QueryParams queryParams);

    Integer countOrders(Integer userId, QueryParams queryParams);
}
