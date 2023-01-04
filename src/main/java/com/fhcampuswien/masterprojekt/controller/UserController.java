package com.fhcampuswien.masterprojekt.controller;

import com.fhcampuswien.masterprojekt.entity.Product;
import com.fhcampuswien.masterprojekt.entity.User;
import com.fhcampuswien.masterprojekt.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("user")
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
        if(userService.checkIfUserExists(user.getUsername())) {
            return new ResponseEntity<>("Username existiert bereits, bitte wählen Sie einen anderen Usernamen aus", HttpStatus.CONFLICT);
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        User newUser = userService.save(user);
        return new ResponseEntity<>(newUser, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public void updateUser(@PathVariable Long id, @RequestBody User user) {
        userService.updateUser(user, id);
        // TODO: check if username already exists
    }

    @DeleteMapping("/{id}")
    public void removeUser(@PathVariable Long id) {
        userService.removeUser(id);
    }

    @PostMapping("/{id}/product")
    public void addProduct(@PathVariable Long id,
                           @RequestBody Product product) {
        userService.addProduct(id, product);
    }

    @DeleteMapping("/{id}/product/{productId}")
    public void removeProduct(@PathVariable Long id,
                              @PathVariable Long productId) {
        userService.removeProduct(id, productId);
    }
}
