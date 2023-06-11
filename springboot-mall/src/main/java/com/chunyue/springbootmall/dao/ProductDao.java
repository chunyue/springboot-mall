package com.chunyue.springbootmall.dao;

import com.chunyue.springbootmall.model.Product;

public interface ProductDao {
    Product getProductById(Integer productId);
}
