package com.fhcampuswien.masterprojekt.repository;

import com.fhcampuswien.masterprojekt.entity.Product;
import com.fhcampuswien.masterprojekt.entity.User;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

  @Query("SELECT u FROM Product u WHERE u.status = 0 AND u.user.id != ?1")
  List<Product> getAllAvailableProducts(Long userId);

  List<Product> findAllByUser(Optional<User> user);

  List<Product> findAllByBoughtByUser(Long userId);

  void deleteByProductId(Long productId);
}
