package com.chunyue.springbootmall.dao;

import com.chunyue.springbootmall.dto.ProductRequest;
import com.chunyue.springbootmall.model.Product;

public interface ProductDao {
    Product getProductById(Integer productId);

    Integer createProduct(ProductRequest productRequest);

    void updateProduct(Integer productId, ProductRequest productRequest);
}
