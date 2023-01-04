package com.fhcampuswien.masterprojekt.service;

import com.fhcampuswien.masterprojekt.Enum.ProductStatus;
import com.fhcampuswien.masterprojekt.entity.Product;
import com.fhcampuswien.masterprojekt.entity.User;
import com.fhcampuswien.masterprojekt.repository.ProductRepository;
import com.fhcampuswien.masterprojekt.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UserService {
    private UserRepository userRepository;
    private ProductRepository productRepository;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public UserService (UserRepository userRepository,
                        ProductRepository productRepository,
                        PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.productRepository = productRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User getUser(Long id) {
        return userRepository.findById(id).get();
    }

    public Product getProduct(Long id) {
        return productRepository.findById(id).get();
    }

    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username).get();
    }

    public User save(User user) {
        user.setFirstname(user.getFirstname());
        user.setLastname(user.getLastname());
        user.setUsername(user.getUsername());
        user.setPassword(user.getPassword());
        return userRepository.save(user);
    }

    public boolean checkIfUserExists(String username) {
        Optional<User> user = userRepository.findByUsername(username);
        return user.isPresent();
    }

    public void updateUser(User user, Long id) {
        User updatedUser = getUser(id);
        updatedUser.setFirstname(user.getFirstname());
        updatedUser.setLastname(user.getLastname());
        updatedUser.setUsername(user.getUsername());
        updatedUser.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(updatedUser);
    }

    // TODO: return statement if user does not exist
    public void removeUser(Long id) {
        User user = getUser(id);
        if(checkIfUserExists(user.getUsername())) {
            userRepository.delete(user);
        }
    }

    // TODO: check if user does not try to add product to someone else
    public void addProduct(Long id, Product product) {
        product.setPrice(product.getPrice());
        product.setProductDescription(product.getProductDescription());
        product.setProductName(product.getProductName());
        product.setStatus(ProductStatus.AVAILABLE);

        User user = getUser(id);
        product.setUser(user);
        user.getProducts().add(product);

        productRepository.save(product);
    }

    // TODO: check if product to remove is assigned to this user
    public void removeProduct(Long id, Long productId) {
//        User user = getUser(id);
//        List<Product> ProductsOfUser = user.getProducts();
        if(productRepository.findById(productId).isPresent()) {
            Product product = productRepository.findById(productId).get();
            productRepository.delete(product);
        }
//        if(ProductsOfUser.contains(product)) {
//        }
    }

    public void updateProduct(Long id, Long productId, Product product) {
        Product updatedProduct = getProduct(productId);
        updatedProduct.setProductName(product.getProductName());
        updatedProduct.setProductDescription(product.getProductDescription());
        updatedProduct.setPrice(product.getPrice());
        updatedProduct.setStatus(ProductStatus.AVAILABLE);

        User user = getUser(id);
        updatedProduct.setUser(user);
        user.getProducts().add(updatedProduct);
    }
}
