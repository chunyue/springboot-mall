package com.chunyue.springbootmall.service.impl;

import com.chunyue.springbootmall.dao.OrderDao;
import com.chunyue.springbootmall.dao.ProductDao;
import com.chunyue.springbootmall.dao.UserDao;
import com.chunyue.springbootmall.dao.jpa.DiscountEntityRepository;
import com.chunyue.springbootmall.dao.jpa.InnerCodeRatioEntityRepository;
import com.chunyue.springbootmall.dto.BuyItem;
import com.chunyue.springbootmall.dto.CreateOrderRequest;
import com.chunyue.springbootmall.dto.QueryParams;
import com.chunyue.springbootmall.model.Order;
import com.chunyue.springbootmall.model.OrderItem;
import com.chunyue.springbootmall.model.Product;
import com.chunyue.springbootmall.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Component
public class OrderServiceImpl implements OrderService {

    private final Logger log = LoggerFactory.getLogger(OrderServiceImpl.class);

    @Autowired
    private OrderDao orderDao;

    @Autowired
    private ProductDao productDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private InnerCodeRatioEntityRepository innerCodeRatioEntityRepository;

    @Autowired
    private DiscountEntityRepository discountEntityRepository;

    @Transactional
    @Override
    public Integer createOrder(Integer userId, CreateOrderRequest createOrderRequest) {

        //檢查是否有這個使用者
        if(userDao.getUserById(userId) == null){
            log.warn("此使用者{}不存在",userId);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        BigDecimal totalAmount = BigDecimal.valueOf(0);
        List<OrderItem> orderItemList = new ArrayList<>();


        for(BuyItem buyItem: createOrderRequest.getOrderItemList()){
            Integer productId = buyItem.getProductId();
            Product product = productDao.getProductById(productId);

            //檢查是否有此product id
            if ( product == null){
                log.warn("此商品ID:{}不存在",productId);
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
            }

            //檢查商品是否庫存足夠
            if (buyItem.getQuantity() > product.getStock()){
                log.warn("此商品:{}，庫存不足，目前庫存為:{}，您想購買{}個",product.getProductName(), product.getStock(),buyItem.getQuantity());
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
            }

            productDao.updateStock(productId, product.getStock() - buyItem.getQuantity());

            BigDecimal productPrice = productDao.getProductById(productId).getPrice();

            if(!createOrderRequest.getDiscountCode().isEmpty()){
                productPrice = productPrice.multiply(getDiscount(createOrderRequest.getDiscountCode()));
            }

            BigDecimal amount = BigDecimal.valueOf(buyItem.getQuantity()).multiply(productPrice);
            totalAmount = totalAmount.add(amount);

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

    @Override
    public Order getOrderById(Integer orderId) {
        Order order = orderDao.getOrderById(orderId);


        List<OrderItem> orderItemList = orderDao.getOrderItemsByOrderId(orderId);

        order.setOrderItemList(orderItemList);

        return order;
    }

    @Override
    public List<Order> getOrdersByUserId(Integer userId, QueryParams queryParams) {
        List<Order> orderList = orderDao.getOrdersByUserId(userId, queryParams);

        for (Order order: orderList){
            List<OrderItem> orderItemList = orderDao.getOrderItemsByOrderId(order.getOrderId());
            order.setOrderItemList(orderItemList);
        }

        return orderList;
    }

    @Override
    public Integer countOrders(Integer userId, QueryParams queryParams) {
        return orderDao.countOrders(userId, queryParams);
    }

    private BigDecimal getDiscount(String discountCode){
        String innerCode = discountEntityRepository.findByDiscountCode(discountCode).getInnerCode();
        return innerCodeRatioEntityRepository.findByInnerCode(innerCode).getDiscountRatio();
    }
}
