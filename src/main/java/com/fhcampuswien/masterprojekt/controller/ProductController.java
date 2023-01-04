package com.fhcampuswien.masterprojekt.controller;

import com.fhcampuswien.masterprojekt.entity.Product;
import com.fhcampuswien.masterprojekt.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("products")
public class ProductController {
    private ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/all-products")
    public ResponseEntity<List<Product>> getAllProducts() {
        List<Product> productList = productService.getAllProducts();
        return new ResponseEntity<>(productList, HttpStatus.OK);
    }

    @GetMapping("/available-products")
    public ResponseEntity<List<Product>> getAvailableProducts() {
        List<Product> availableProductList = productService.getAvailableProducts();
        return new ResponseEntity<>(availableProductList, HttpStatus.OK);
    }

//    @GetMapping()
//    public ResponseEntity<List<Product>> getAllProductsOfUser(@RequestParam String username) {
//        List<Product> productList = productService.getAllProductsOfUser(username);
//        return new ResponseEntity<>(productList, HttpStatus.OK);
//    }

}
