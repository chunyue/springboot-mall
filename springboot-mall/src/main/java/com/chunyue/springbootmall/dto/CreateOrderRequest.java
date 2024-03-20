package com.chunyue.springbootmall.dto;

import lombok.Getter;

import javax.validation.constraints.NotEmpty;
import java.util.List;

@Getter
public class CreateOrderRequest {

    private String discountCode;
    @NotEmpty
    private List<BuyItem> orderItemList;

    public List<BuyItem> getOrderItemList() {
        return orderItemList;
    }

    public void setOrderItemList(List<BuyItem> orderItemList) {
        this.orderItemList = orderItemList;
    }
}
