package com.chunyue.springbootmall.controller;

import com.chunyue.springbootmall.dao.jpa.DiscountEntity;
import com.chunyue.springbootmall.service.DiscountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.web.PageableDefault;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.criteria.Predicate;
import java.util.Optional;
import java.util.stream.Stream;

@Validated
@RestController
public class DiscountController {

    @Autowired
    private DiscountService discountService;

    @GetMapping("/discountCodeRecords")
    public Page<DiscountEntity> getDiscountCode(@RequestParam(required = false)  String discountCode,
                                                @RequestParam(required = false)  String innerCode,
                                                @RequestParam(required = false)  String startDate,
                                                @RequestParam(required = false)  String endedDate,
                                                @RequestParam(name = "page", defaultValue = "0") int pageNumber,
                                                @RequestParam(name = "size", defaultValue = "10") int pageSize,
                                                @RequestParam(name = "sort", defaultValue = "id", required = false) String sort
                                                                ){
        var spec =  Specification.<DiscountEntity>where((root, criteriaQuery,criteriaBuilder) -> {
            var predicates = Stream.of(
            Optional.ofNullable(discountCode).map(v -> criteriaBuilder.like(root.get("discountCode"),"%" + discountCode + "%")),
            Optional.ofNullable(innerCode).map(v -> criteriaBuilder.like(root.get("discountCode"),"%" + innerCode + "%")),
            Optional.ofNullable(startDate).map(v -> criteriaBuilder.greaterThanOrEqualTo(root.get("discountCode"),startDate)),
            Optional.ofNullable(endedDate).map(v -> criteriaBuilder.greaterThanOrEqualTo(root.get("discountCode"),endedDate))
            ).filter(Optional::isPresent)
                    .map(Optional::get)
                    .toArray(Predicate[]::new);

            return criteriaBuilder.and(predicates);
        });

        Pageable pageable = PageRequest.of(pageNumber,pageSize, Sort.by(sort));

        return discountService.findAll(spec,pageable);
    }
}