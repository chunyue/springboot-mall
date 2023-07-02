package com.chunyue.springbootmall.service;

import com.chunyue.springbootmall.constant.ProductCategory;
import com.chunyue.springbootmall.dto.ProductRequest;
import com.chunyue.springbootmall.model.Product;

import java.util.List;

public interface ProductService {

    List<Product> getProducts(ProductCategory category);

    Product getProductById(Integer productId);

    Integer createProduct(ProductRequest productRequest);

    void updateProduct(Integer productId, ProductRequest productRequest);

    void deleteByProductId(Integer productId);

}
