package com.chunyue.springbootmall.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.core.io.Resource;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ResourceDetails {
    @JsonIgnore
    Resource resource;
    String fileName;
}
