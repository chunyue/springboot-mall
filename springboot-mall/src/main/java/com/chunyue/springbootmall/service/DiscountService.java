package com.chunyue.springbootmall.service;

import com.chunyue.springbootmall.dao.jpa.DiscountEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

public interface DiscountService {

    Page<DiscountEntity> findAll(Specification<DiscountEntity> spec, Pageable pageable);
}
