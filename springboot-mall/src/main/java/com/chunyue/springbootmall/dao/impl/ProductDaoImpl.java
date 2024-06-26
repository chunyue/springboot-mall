package com.chunyue.springbootmall.dao.impl;

import com.chunyue.springbootmall.dao.ProductDao;
import com.chunyue.springbootmall.dto.ProductQueryParams;
import com.chunyue.springbootmall.dto.ProductRequest;
import com.chunyue.springbootmall.model.Product;
import com.chunyue.springbootmall.rowmapper.ProductRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class ProductDaoImpl implements ProductDao {

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public Integer countProduct(ProductQueryParams productQueryParams) {
        String sql = "SELECT COUNT(*) FROM mall.product WHERE 1=1";

        Map<String, Object> map = new HashMap();

        //查詢條件
        sql = addFilteringSql(sql, map, productQueryParams);

        Integer total = namedParameterJdbcTemplate.queryForObject(sql, map, Integer.class);
        return total;
    }

    public List<Product> getProducts(ProductQueryParams productQueryParams){
        String sql = "SELECT product_id, product_name, category, image_url, price, stock, " +
                "description, created_date, last_modified_date " +
                "FROM mall.product " +
                "WHERE 1 = 1";
        Map<String, Object> map = new HashMap();

        //查詢條件
        sql = addFilteringSql(sql, map, productQueryParams);

        sql += " ORDER BY " + productQueryParams.getOrderBy() + " " + productQueryParams.getSort();

        sql += " LIMIT :limit OFFSET :offset";
        map.put("limit",productQueryParams.getLimit());
        map.put("offset",productQueryParams.getOffset());

        List<Product> productList = namedParameterJdbcTemplate.query(sql, map ,new ProductRowMapper());

        return productList;
    }

    @Override
    public Product getProductById(Integer productId) {
        String sql = "select product_id, product_name, category, image_url, price, stock, description, created_date, last_modified_date " +
                "from mall.product " +
                "where product_id = :productId;";

        Map<String, Object> map = new HashMap<>();
        map.put("productId", productId);

        List<Product> productList = namedParameterJdbcTemplate.query(sql, map, new ProductRowMapper());

        if(productList.size() > 0 ){
            return productList.get(0);
        }return null;

    }

    @Override
    public Integer createProduct(ProductRequest productRequest) {

        String sql = "INSERT INTO mall.product(product_name, category, image_url, "+
                "price, stock, description, created_date, last_modified_date) "+
                "VALUES (:productName, :category, :imageUrl, :price, "+
                ":stock, :description, :createDate, :lastModifiedDate)";

        Map<String, Object> map = new HashMap<>();
        map.put("productName",productRequest.getProductName());

        map.put("category",productRequest.getCategory().name());

        map.put("imageUrl",productRequest.getImageUrl());
        map.put("price",productRequest.getPrice());
        map.put("stock",productRequest.getStock());
        map.put("description",productRequest.getDescription());

        Date now = new Date();
        map.put("createDate", now);
        map.put("lastModifiedDate", now);

        KeyHolder keyHolder = new GeneratedKeyHolder();

        namedParameterJdbcTemplate.update(sql, new MapSqlParameterSource(map), keyHolder);

        var productId = keyHolder.getKeyList().get(0).get("product_id");

        return Integer.parseInt(productId.toString());
    }

    @Override
    public void updateProduct(Integer productId, ProductRequest productRequest) {
        String sql = "UPDATE mall.product SET product_name = :productName, category = :category, image_url = :imageUrl, "+
                "price = :price, stock = :stock, description = :description, last_modified_date = :lastModifiedDate "+
                "WHERE product_id = :productId";

        Map<String, Object> map = new HashMap<>();
        map.put("productId", productId);

        map.put("productName",productRequest.getProductName());
        map.put("category",productRequest.getCategory().name());
        map.put("imageUrl",productRequest.getImageUrl());
        map.put("price",productRequest.getPrice());
        map.put("stock",productRequest.getStock());
        map.put("description",productRequest.getDescription());

        map.put("lastModifiedDate", new Date());

        namedParameterJdbcTemplate.update(sql, map);

    }

    @Override
    public void updateStock(Integer productId, Integer stockNumber) {
        String sql = "UPDATE mall.PRODUCT "+
                "SET stock = :stockNumber, last_modified_date = :now " +
                "WHERE product_id = :productId";
        Map<String, Object> map = new HashMap<>();
        map.put("stockNumber", stockNumber);
        map.put("now", new Date());
        map.put("productId", productId);

        namedParameterJdbcTemplate.update(sql,map);
    }

    @Override
    public void deleteByProductId(Integer productId) {
        String sql = "DELETE FROM mall.product WHERE product_id = :productId";

        Map<String, Object> map = new HashMap<>();
        map.put("productId", productId);

        namedParameterJdbcTemplate.update(sql, map);
    }

    private String addFilteringSql(String sql, Map<String, Object> map, ProductQueryParams productQueryParams){
        if(productQueryParams.getProductCategory() != null){
            sql = sql + " AND category = :category";
            map.put("category",productQueryParams.getProductCategory().name());
        }

        if(productQueryParams.getSearch() != null){
            sql += " AND product_name LIKE :search";
            map.put("search", "%" + productQueryParams.getSearch() + "%");
        }

        return sql;
    }
}
