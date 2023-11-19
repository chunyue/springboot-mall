package com.chunyue.springbootmall.controller;

import com.chunyue.springbootmall.dto.BuyItem;
import com.chunyue.springbootmall.dto.CreateOrderRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Transactional
    @Test
    void createOrder_success() throws Exception {
        CreateOrderRequest createOrderRequest = new CreateOrderRequest();
        List<BuyItem> orderItemList =new ArrayList<>();
        BuyItem buyItem = new BuyItem();
        buyItem.setProductId(1);
        buyItem.setQuantity(1);
        orderItemList.add(buyItem);
        createOrderRequest.setOrderItemList(orderItemList);

        ObjectMapper objectMapper = new ObjectMapper();
        String createOrderRequestJson = objectMapper.writeValueAsString(createOrderRequest);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/users/{userId}/orders", 1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(createOrderRequestJson);

        mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().is(201))
                .andExpect(jsonPath("$.orderId", notNullValue()))
                .andExpect(jsonPath("$.userId",notNullValue()))
                .andExpect(jsonPath("$.totalAmount",notNullValue()))
                .andExpect(jsonPath("$.createdDate",notNullValue()))
                .andExpect(jsonPath("$.lastModifiedDay",notNullValue()))
                .andExpect(jsonPath("$.orderItemList[0].productId",equalTo(1)));
    }

    @Test
    void createOrder_noUser() throws Exception {
        CreateOrderRequest createOrderRequest = new CreateOrderRequest();
        List<BuyItem> orderItemList =new ArrayList<>();
        BuyItem buyItem = new BuyItem();
        buyItem.setProductId(1);
        buyItem.setQuantity(1);
        orderItemList.add(buyItem);
        createOrderRequest.setOrderItemList(orderItemList);

        ObjectMapper objectMapper = new ObjectMapper();
        String createOrderRequestJson = objectMapper.writeValueAsString(createOrderRequest);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/users/{userId}/orders", 1000)
                .contentType(MediaType.APPLICATION_JSON)
                .content(createOrderRequestJson);

        mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().is(400));
    }

    @Test
    void createOrder_shortageOfStock() throws Exception {
        CreateOrderRequest createOrderRequest = new CreateOrderRequest();
        List<BuyItem> orderItemList =new ArrayList<>();
        BuyItem buyItem = new BuyItem();
        buyItem.setProductId(1);
        buyItem.setQuantity(1000);
        orderItemList.add(buyItem);
        createOrderRequest.setOrderItemList(orderItemList);

        ObjectMapper objectMapper = new ObjectMapper();
        String createOrderRequestJson = objectMapper.writeValueAsString(createOrderRequest);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/users/{userId}/orders", 1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(createOrderRequestJson);

        mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().is(400));
    }

    @Test
    void createOrder_noProductId() throws Exception {
        CreateOrderRequest createOrderRequest = new CreateOrderRequest();
        List<BuyItem> orderItemList =new ArrayList<>();
        BuyItem buyItem = new BuyItem();
        buyItem.setProductId(1000);
        buyItem.setQuantity(1);
        orderItemList.add(buyItem);
        createOrderRequest.setOrderItemList(orderItemList);

        ObjectMapper objectMapper = new ObjectMapper();
        String createOrderRequestJson = objectMapper.writeValueAsString(createOrderRequest);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/users/{userId}/orders", 1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(createOrderRequestJson);

        mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().is(400));
    }

    @Transactional
    @Test
    void getOrders_success() throws Exception {
        CreateOrderRequest createOrderRequest = new CreateOrderRequest();
        List<BuyItem> orderItemList =new ArrayList<>();
        BuyItem buyItem = new BuyItem();
        buyItem.setProductId(1);
        buyItem.setQuantity(1);
        orderItemList.add(buyItem);
        createOrderRequest.setOrderItemList(orderItemList);

        ObjectMapper objectMapper = new ObjectMapper();
        String createOrderRequestJson = objectMapper.writeValueAsString(createOrderRequest);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/users/{userId}/orders", 1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(createOrderRequestJson);

        mockMvc.perform(requestBuilder);

        RequestBuilder requestBuilder1 = MockMvcRequestBuilders
                .get("/users/{userId}/orders", 1);

        mockMvc.perform(requestBuilder1)
                .andDo(print())
                .andDo(print())
                .andExpect(status().is(200))
                .andExpect(jsonPath("$.results[0].orderItemList[0]",notNullValue()));
    }
}