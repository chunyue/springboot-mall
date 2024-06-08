package com.chunyue.springbootmall.controller;

import com.chunyue.springbootmall.constant.ProductCategory;
import com.chunyue.springbootmall.dto.ProductQueryParams;
import com.chunyue.springbootmall.dto.ProductRequest;
import com.chunyue.springbootmall.model.Product;
import com.chunyue.springbootmall.service.ProductService;
import com.chunyue.springbootmall.util.Page;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.List;

@Validated
@RestController
public class ProductController {

    @Autowired
    private ProductService productService;

    @Operation(summary = "取得多筆產品資訊", description = "根據條件取得多筆產品資訊")
    @GetMapping("/products")
    public ResponseEntity<Page<Product>> getProducts(
            @RequestParam(required = false) ProductCategory category,
            @RequestParam(required = false) String search,
            @RequestParam(defaultValue = "created_date") String orderBy,
            @RequestParam(defaultValue = "desc") String sort,
            @RequestParam(defaultValue = "5") @Max(1000) @Min(0) Integer limit,
            @RequestParam(defaultValue = "0") @Min(0) Integer offset
            ){

        ProductQueryParams productQueryParams = new ProductQueryParams();
        productQueryParams.setProductCategory(category);
        productQueryParams.setSearch(search);
        productQueryParams.setOrderBy(orderBy);
        productQueryParams.setSort(sort);
        productQueryParams.setLimit(limit);
        productQueryParams.setOffset(offset);

        // 取得商品列表
        List<Product> productList = productService.getProducts(productQueryParams);

        // 取得商品總數
        Integer total = productService.countProduct(productQueryParams);

        // 分頁
        Page<Product> page = new Page<>();
        page.setLimit(limit);
        page.setOffset(offset);
        page.setTotal(total);
        page.setResults(productList);

        return ResponseEntity.status(HttpStatus.OK).body(page);
    }

    @Operation(summary = "取得產品資訊", description = "根據產品ID取得產品資訊")
    @GetMapping("/products/{productId}")
    public ResponseEntity<Product> getProduct(@PathVariable Integer productId){
        Product product = productService.getProductById(productId);

        if(product != null){
            return ResponseEntity.status(HttpStatus.OK).body(product);
        } return ResponseEntity.status(HttpStatus.NOT_FOUND).build();

    }

    @Operation(summary = "新增一筆產品資訊", description = "新增產品（名稱、類別、圖片、價錢、庫存、描述）")
    @PostMapping("/products")
    public ResponseEntity<Product> createProduct(@RequestBody @Valid ProductRequest productRequest){
        Integer productId = productService.createProduct(productRequest);

        Product product = productService.getProductById(productId);

        ResponseEntity<Product> responseEntity = ResponseEntity.status(HttpStatus.CREATED).body(product);

        return responseEntity;

    }

    @Operation(summary = "更新產品資訊", description = "更新產品名稱、類別、價錢、庫存、描述")
    @PutMapping("/products/{productId}")
    public ResponseEntity<Product> updateProduct(@PathVariable Integer productId,
                                                 @RequestBody @Valid ProductRequest productRequest){
        //檢查商品是否已存在，若不存在回傳404給前端
        Product product = productService.getProductById(productId);

        if(product == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        //修改商品
        productService.updateProduct(productId, productRequest);

        Product updatedproduct = productService.getProductById(productId);

        return ResponseEntity.status(HttpStatus.OK).body(updatedproduct);
    }

    @Operation(summary = "刪除單筆產品", description = "根據ID刪除產品")
    @DeleteMapping("/products/{productId}")
    public ResponseEntity<?> delectByProductId(@PathVariable Integer productId){
        productService.deleteByProductId(productId);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();

    }
}
