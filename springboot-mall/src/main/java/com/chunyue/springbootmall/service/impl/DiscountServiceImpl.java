package com.chunyue.springbootmall.service.impl;

import com.chunyue.springbootmall.dao.jpa.DiscountEntityRepository;
import com.chunyue.springbootmall.dao.jpa.DiscountEntity;
import com.chunyue.springbootmall.dto.DiscountQueryRequest;
import com.chunyue.springbootmall.service.DiscountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class DiscountServiceImpl implements DiscountService {

    @Autowired
    private DiscountEntityRepository DiscountEntityRepository;

    @Override
    public List<DiscountEntity> getDiscountsByQueryParams(DiscountQueryRequest discountQueryRequest) {

        List<DiscountEntity> discountEntityList = new ArrayList<>();

        Optional<DiscountEntity> discountEntity = DiscountEntityRepository.findDiscountEntitiesByDiscountCodeLikeAndInnerCodeLikeAndStartedDateGreaterThanEqualAndEndDateLessThanEqual(
                discountQueryRequest.getDiscountCode(), discountQueryRequest.getInnerCode(), discountQueryRequest.getStartedDate(), discountQueryRequest.getEndDate());
        discountEntity.stream().map(discountEntityList::add).findAny().orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));


        return discountEntityList;
    }
}
