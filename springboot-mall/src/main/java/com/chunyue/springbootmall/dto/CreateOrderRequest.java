package com.chunyue.springbootmall.dto;

import javax.validation.constraints.NotEmpty;
import java.util.List;

public class CreateOrderRequest {

    @NotEmpty
    private List<BuyItem> orderItemList;

    public List<BuyItem> getOrderItemList() {
        return orderItemList;
    }

    public void setOrderItemList(List<BuyItem> orderItemList) {
        this.orderItemList = orderItemList;
    }
}
