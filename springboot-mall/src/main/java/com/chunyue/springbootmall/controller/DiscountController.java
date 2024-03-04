package com.chunyue.springbootmall.controller;

import com.chunyue.springbootmall.dao.jpa.DiscountEntity;
import com.chunyue.springbootmall.dto.DiscountQueryRequest;
import com.chunyue.springbootmall.service.DiscountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController
public class DiscountController {

    @Autowired
    private DiscountService discountService;

    @GetMapping("/discountCodeRecords")
    public ResponseEntity<List<DiscountEntity>> getDiscountCode(@RequestParam String discountCode,
                                                                @RequestParam String innerCode,
                                                                @RequestParam String startDate,
                                                                @RequestParam String endedDate
                                                                ){

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate startedDate = LocalDate.parse(startDate, formatter);
        LocalDate endDate = LocalDate.parse(endedDate, formatter);
        DiscountQueryRequest discountQueryRequest = new DiscountQueryRequest().builder()
                .discountCode(discountCode).innerCode(innerCode).startedDate(startedDate).endDate(endDate).build();
        List<DiscountEntity> discountEntity = discountService.getDiscountsByQueryParams(discountQueryRequest);

        return ResponseEntity.status(HttpStatus.OK).body(discountEntity);

    }
}
