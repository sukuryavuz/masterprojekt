package com.fhcampuswien.masterprojekt.service;

import com.fhcampuswien.masterprojekt.Enum.ProductStatus;
import com.fhcampuswien.masterprojekt.entity.Product;
import com.fhcampuswien.masterprojekt.entity.User;
import com.fhcampuswien.masterprojekt.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
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
        throw new IllegalArgumentException("Produkt existiert nicht.");
    }

    public boolean checkIfProductExists(Long id) {
        Optional<Product> product = this.productRepository.findById(id);
        return product.isPresent();
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public List<Product> getAvailableProducts() {
        return productRepository.findByStatus(ProductStatus.AVAILABLE);
    }

//    public List<Product> getAllProductsOfUser(String username) {
//    }


}
