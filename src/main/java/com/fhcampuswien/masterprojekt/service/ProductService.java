package com.fhcampuswien.masterprojekt.service;

import com.fhcampuswien.masterprojekt.entity.Product;
import com.fhcampuswien.masterprojekt.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProductService {
    private ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Product findById(Long id) {
        if(checkIfProductExists(id)) {
            return this.productRepository.findById(id).get();
        }
        throw new IllegalArgumentException("Product is not present");
    }

    public boolean checkIfProductExists(Long id) {
        Optional<Product> product = this.productRepository.findById(id);
        return product.isPresent();
    }
}
