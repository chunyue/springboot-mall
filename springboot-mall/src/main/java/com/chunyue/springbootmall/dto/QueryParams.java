package com.chunyue.springbootmall.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class QueryParams {

    private Integer limit;

    private Integer offset;
}
