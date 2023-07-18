package com.chunyue.springbootmall.service.impl;

import com.chunyue.springbootmall.dao.ProductDao;
import com.chunyue.springbootmall.dto.ProductQueryParams;
import com.chunyue.springbootmall.dto.ProductRequest;
import com.chunyue.springbootmall.model.Product;
import com.chunyue.springbootmall.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductDao productDao;

    @Override
    public Integer countProduct(ProductQueryParams productQueryParams) {
        return productDao.countProduct(productQueryParams);
    }

    @Override
    public List<Product> getProducts(ProductQueryParams productQueryParams) {

       List<Product> productList =  productDao.getProducts(productQueryParams);
        return productList;
    }

    @Override
    public Product getProductById(Integer productId) {
        return productDao.getProductById(productId);
    }


    @Override
    public Integer createProduct(ProductRequest productRequest) {
        return productDao.createProduct(productRequest);
    }

    @Override
    public void updateProduct(Integer productId, ProductRequest productRequest) {
        productDao.updateProduct(productId, productRequest);
    }

    @Override
    public void deleteByProductId(Integer productId) {
        productDao.deleteByProductId(productId);
    }
}
