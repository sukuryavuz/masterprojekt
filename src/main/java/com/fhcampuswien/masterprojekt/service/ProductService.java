package com.fhcampuswien.masterprojekt.service;

import com.fhcampuswien.masterprojekt.entity.Product;
import com.fhcampuswien.masterprojekt.entity.User;
import com.fhcampuswien.masterprojekt.repository.ProductRepository;
import com.fhcampuswien.masterprojekt.repository.UserRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductService {
  private ProductRepository productRepository;
  private UserRepository userRepository;

  @Autowired
  public ProductService(ProductRepository productRepository, UserRepository userRepository) {
    this.productRepository = productRepository;
    this.userRepository = userRepository;
  }

  public Product findById(Long id) {
    if (checkIfProductExists(id)) {
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

  public List<Product> getAvailableProducts(Long userId) {
    return productRepository.getAllAvailableProducts(userId);
  }

  public List<Product> getAllProductsOfUser(Long userId) {
    Optional<User> user = userRepository.findById(userId);
    return productRepository.findAllByUser(user);
  }
}
