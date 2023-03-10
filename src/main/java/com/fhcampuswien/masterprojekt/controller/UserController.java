package com.fhcampuswien.masterprojekt.controller;

import com.fhcampuswien.masterprojekt.entity.Product;
import com.fhcampuswien.masterprojekt.entity.User;
import com.fhcampuswien.masterprojekt.service.UserService;
import java.io.IOException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/user")
public class UserController {
  private UserService userService;
  private PasswordEncoder passwordEncoder;

  @Autowired
  public UserController(UserService userService, PasswordEncoder passwordEncoder) {
    this.userService = userService;
    this.passwordEncoder = passwordEncoder;
  }

  @PostMapping("/register")
  public ResponseEntity<?> register(@RequestBody User user) {
    if (userService.checkIfUserExists(user.getUsername())) {
      return new ResponseEntity<>(
          "Username existiert bereits, bitte wählen Sie einen anderen Usernamen aus",
          HttpStatus.CONFLICT);
    }
    user.setPassword(passwordEncoder.encode(user.getPassword()));
    User newUser = userService.save(user);
    return new ResponseEntity<>(newUser, HttpStatus.OK);
  }

  @GetMapping("/{userId}")
  public ResponseEntity<User> getUser(@PathVariable Long userId) {
    User user = userService.getUser(userId);
    return new ResponseEntity<>(user, HttpStatus.OK);
  }

  @PutMapping("/{userId}")
  public void updateUser(@PathVariable Long userId, @RequestBody User user) {
    userService.updateUser(user, userId);
  }

  @DeleteMapping("/{userId}")
  public void removeUser(@PathVariable Long userId) {
    userService.removeUser(userId);
  }

  @PostMapping(value = "/{userId}/product")
  public void addProduct(
      @PathVariable Long userId,
      @RequestParam("productName") String productName,
      @RequestParam("productDescription") String productDescription,
      @RequestParam("price") double price,
      @RequestParam(value = "file", required = false) MultipartFile file)
      throws IOException {
    Product product = new Product();
    product.setProductName(productName);
    product.setProductDescription(productDescription);
    product.setPrice(price);
    if (!(file == null)) {
      product.setFile(file.getBytes());
    }
    userService.addProduct(userId, product);
  }

  @DeleteMapping("/{userId}/product/{productId}")
  public void removeProduct(@PathVariable Long userId, @PathVariable Long productId) {
    userService.removeProduct(userId, productId);
  }

  @PutMapping("/{userId}/product/{productId}")
  public void updateProduct(
      @PathVariable Long userId, @PathVariable Long productId, @RequestBody Product product) {
    userService.updateProduct(userId, productId, product);
  }

  @PostMapping("/{userId}/product/{productId}")
  public void buyProduct(@PathVariable Long userId, @PathVariable Long productId) {
    userService.buyProduct(userId, productId);
  }

  @GetMapping("{userId}/products")
  public ResponseEntity<List<Product>> getMyBoughtProducts(@PathVariable Long userId) {
    List<Product> myBoughtProducts = userService.getMyBoughtProducts(userId);
    return new ResponseEntity<>(myBoughtProducts, HttpStatus.OK);
  }
}
