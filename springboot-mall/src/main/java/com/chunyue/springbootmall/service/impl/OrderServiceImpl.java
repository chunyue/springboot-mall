package com.chunyue.springbootmall.service.impl;

import com.chunyue.springbootmall.dao.OrderDao;
import com.chunyue.springbootmall.dao.ProductDao;
import com.chunyue.springbootmall.dto.BuyItem;
import com.chunyue.springbootmall.dto.CreateOrderRequest;
import com.chunyue.springbootmall.model.OrderItem;
import com.chunyue.springbootmall.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderDao orderDao;

    @Autowired
    private ProductDao productDao;

    @Override
    public Integer createOrder(Integer userId, CreateOrderRequest createOrderRequest) {
        Integer totalAmount = 0;
        List<OrderItem> orderItemList = new ArrayList<>();

        for(BuyItem buyItem: createOrderRequest.getOrderItemList()){
            Integer productId = buyItem.getProductId();
            Integer productPrice = productDao.getProductById(productId).getPrice();

            Integer amount = buyItem.getQuantity() * productPrice;
            totalAmount += amount;

            OrderItem orderItem = new OrderItem();
            orderItem.setProductId(productId);
            orderItem.setQuantity(buyItem.getQuantity());
            orderItem.setAmount(amount);
            orderItemList.add(orderItem);

        }

        Integer orderId = orderDao.createOrder(userId, totalAmount);

        orderDao.createOrderItems(orderId, orderItemList);

        return orderId;
    }
}
