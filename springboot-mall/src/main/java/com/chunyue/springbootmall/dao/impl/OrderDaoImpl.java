package com.chunyue.springbootmall.dao.impl;

import com.chunyue.springbootmall.dao.OrderDao;
import com.chunyue.springbootmall.dto.QueryParams;
import com.chunyue.springbootmall.model.Order;
import com.chunyue.springbootmall.model.OrderItem;
import com.chunyue.springbootmall.rowmapper.OrderItemRowMapper;
import com.chunyue.springbootmall.rowmapper.OrderRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class OrderDaoImpl implements OrderDao {

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public Integer createOrder(Integer userId, BigDecimal amount) {
        String sql = "INSERT INTO mall.\"order\" (user_id, total_amount, created_date, last_modified_date) " +
                "VALUES (:userId, :totalAmount, :createdDate, :lastModifiedDate) ;";

        Map<String, Object> map = new HashMap<>();
        map.put("userId", userId);
        map.put("totalAmount", amount);

        Date now = new Date();
        map.put("createdDate", now);
        map.put("lastModifiedDate", now);

        KeyHolder keyHolder = new GeneratedKeyHolder();

        namedParameterJdbcTemplate.update(sql, new MapSqlParameterSource(map), keyHolder);
        var id = keyHolder.getKeyList().get(0).get("order_id");

        return Integer.parseInt(id.toString());
    }

    @Override
    public void createOrderItems(Integer orderId, List<OrderItem> orderItemList) {
        String sql = "INSERT INTO mall.order_item(order_id, product_id, quantity, amount) " +
                "VALUES (:orderId, :productId, :quantity, :amount) ;";

        MapSqlParameterSource[] parameterSources = new MapSqlParameterSource[orderItemList.size()];

        for (int i = 0 ; i < orderItemList.size(); i++){
            OrderItem orderItem = orderItemList.get(i);
            parameterSources[i] = new MapSqlParameterSource();
            parameterSources[i].addValue("orderId",orderId);
            parameterSources[i].addValue("productId", orderItem.getProductId());
            parameterSources[i].addValue("quantity", orderItem.getQuantity());
            parameterSources[i].addValue("amount", orderItem.getAmount());
        }

        namedParameterJdbcTemplate.batchUpdate(sql, parameterSources);


    }

    @Override
    public Order getOrderById(Integer orderId) {
        String sql = "SELECT order_id, user_id, total_amount, created_date, last_modified_date " +
                "FROM mall.\"order\" WHERE order_id = :orderId;";

        Map<String, Object> map = new HashMap<>();
        map.put("orderId", orderId);

        List<Order> OrderList = namedParameterJdbcTemplate.query(sql,map,new OrderRowMapper());

        if(OrderList.size() > 0 ){
            return OrderList.get(0);
        }else {
            return null;
        }

    }

    @Override
    public List<OrderItem> getOrderItemsByOrderId(Integer orderId) {
        String sql = "select oi.order_item_id, oi.order_id, oi.product_id, oi.quantity, oi.amount, p.product_name, p.image_url "
                + "FROM mall.order_item oi "
                + "LEFT JOIN mall.product p ON oi.product_id = p.product_id "
                + "WHERE order_id = :orderId;";
        Map<String, Object> map = new HashMap<>();
        map.put("orderId", orderId);

        List<OrderItem> orderItemListList = namedParameterJdbcTemplate.query(sql,map,new OrderItemRowMapper());

            return orderItemListList;
    }

    @Override
    public List<Order> getOrdersByUserId(Integer userId, QueryParams queryParams) {
        String sql = "SELECT order_id, user_id, total_amount, created_date, last_modified_date " +
                "FROM mall.\"order\" WHERE user_id = :userId ";

        sql += "ORDER BY created_date DESC ";
        sql += "LIMIT :limit OFFSET :offset";

        Map<String, Object> map = new HashMap<>();
        map.put("userId", userId);
        map.put("limit", queryParams.getLimit());
        map.put("offset", queryParams.getOffset());

        List<Order> OrderList = namedParameterJdbcTemplate.query(sql,map,new OrderRowMapper());

        if (OrderList.size() > 0){
            return OrderList;
        }else {
            return null;
        }
    }

    public Integer countOrders(Integer userId, QueryParams queryParams) {
        String sql = "SELECT count(*) FROM mall.\"order\" WHERE user_id = :userId ";

        Map<String, Object> map = new HashMap<>();
        map.put("userId", userId);

        return namedParameterJdbcTemplate.queryForObject(sql, map, Integer.class);
    }
}
