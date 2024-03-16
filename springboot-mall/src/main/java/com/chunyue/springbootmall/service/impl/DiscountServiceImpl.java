package com.chunyue.springbootmall.service.impl;

import com.chunyue.springbootmall.dao.jpa.DiscountEntityRepository;
import com.chunyue.springbootmall.dao.jpa.DiscountEntity;
import com.chunyue.springbootmall.service.DiscountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
public class DiscountServiceImpl implements DiscountService {

    @Autowired
    private DiscountEntityRepository DiscountEntityRepository;

    @Override
    public Page<DiscountEntity> findAll(Specification<DiscountEntity> spec, Pageable pageable) {
        return DiscountEntityRepository.findAll(spec, pageable);
    }
}
