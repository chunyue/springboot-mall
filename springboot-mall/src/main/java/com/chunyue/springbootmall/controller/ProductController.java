package com.chunyue.springbootmall.controller;

import com.chunyue.springbootmall.dto.ProductRequest;
import com.chunyue.springbootmall.model.Product;
import com.chunyue.springbootmall.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("/products")
    public ResponseEntity<List<Product>> getProducts(){

        List<Product> productList = productService.getProducts();

        return ResponseEntity.status(HttpStatus.OK).body(productList);
    }

    @GetMapping("products/{productId}")
    public ResponseEntity<Product> getProduct(@PathVariable Integer productId){
        Product product = productService.getProductById(productId);

        if(product != null){
            return ResponseEntity.status(HttpStatus.OK).body(product);
        } return ResponseEntity.status(HttpStatus.NOT_FOUND).build();

    }

    @PostMapping("products")
    public ResponseEntity<Product> createProduct(@RequestBody @Valid ProductRequest productRequest){
        Integer productId = productService.createProduct(productRequest);

        Product product = productService.getProductById(productId);

        ResponseEntity<Product> responseEntity = ResponseEntity.status(HttpStatus.CREATED).body(product);

        return responseEntity;

    }

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

    @DeleteMapping("products/{productId}")
    public ResponseEntity<?> delectByProductId(@PathVariable Integer productId){
        productService.deleteByProductId(productId);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();

    }
}
