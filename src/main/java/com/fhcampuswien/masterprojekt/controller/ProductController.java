package com.fhcampuswien.masterprojekt.controller;

import com.fhcampuswien.masterprojekt.entity.Product;
import com.fhcampuswien.masterprojekt.service.ProductService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/products")
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

  @GetMapping("/{userId}/available-products")
  public ResponseEntity<List<Product>> getAvailableProducts(@PathVariable Long userId) {
    List<Product> availableProductList = productService.getAvailableProducts(userId);
    return new ResponseEntity<>(availableProductList, HttpStatus.OK);
  }

  @GetMapping("/{userId}")
  public ResponseEntity<List<Product>> getAllProductsOfUser(@PathVariable Long userId) {
    List<Product> productList = productService.getAllProductsOfUser(userId);
    return new ResponseEntity<>(productList, HttpStatus.OK);
  }
}
