package com.chunyue.springbootmall.service;

import com.chunyue.springbootmall.dto.ProductRequest;
import com.chunyue.springbootmall.model.Product;

public interface ProductService {
    Product getProductById(Integer productId);

    Integer createProduct(ProductRequest productRequest);

    void updateProduct(Integer productId, ProductRequest productRequest);

    void deleteByProductId(Integer productId);

}
