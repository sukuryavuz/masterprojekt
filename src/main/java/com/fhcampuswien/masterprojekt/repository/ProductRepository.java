package com.fhcampuswien.masterprojekt.repository;

import com.fhcampuswien.masterprojekt.Enum.ProductStatus;
import com.fhcampuswien.masterprojekt.entity.Product;
import com.fhcampuswien.masterprojekt.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByStatus(ProductStatus status);
    List<Product> findAllByUser(Optional<User> user);
    List<Product> findAllByBoughtByUser(Long userId);
    void deleteByProductId(Long productId);
}
