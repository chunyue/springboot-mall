package com.chunyue.springbootmall.service;

import com.chunyue.springbootmall.dto.ProductQueryParams;
import com.chunyue.springbootmall.dto.ProductRequest;
import com.chunyue.springbootmall.model.Product;

import java.util.List;

public interface ProductService {

    Integer countProduct(ProductQueryParams productQueryParams);

    List<Product> getProducts(ProductQueryParams productQueryParams);

    Product getProductById(Integer productId);

    Integer createProduct(ProductRequest productRequest);

    void updateProduct(Integer productId, ProductRequest productRequest);

    void deleteByProductId(Integer productId);

}
