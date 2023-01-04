package com.fhcampuswien.masterprojekt.repository;

import com.fhcampuswien.masterprojekt.Enum.ProductStatus;
import com.fhcampuswien.masterprojekt.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByStatus(ProductStatus status);
}
