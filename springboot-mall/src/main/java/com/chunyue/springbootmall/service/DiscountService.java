package com.chunyue.springbootmall.service;

import com.chunyue.springbootmall.dao.jpa.DiscountEntity;
import com.chunyue.springbootmall.dto.DiscountQueryRequest;


import java.util.List;

public interface DiscountService {

    List<DiscountEntity> getDiscountsByQueryParams(DiscountQueryRequest discountQueryRequest);
}
