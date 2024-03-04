package com.chunyue.springbootmall.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DiscountQueryRequest {

    private String discountCode;
    private String innerCode;
    private LocalDate startedDate;
    private LocalDate endDate;

}
