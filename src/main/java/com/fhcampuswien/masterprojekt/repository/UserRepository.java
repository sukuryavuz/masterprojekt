package com.fhcampuswien.masterprojekt.repository;

import com.fhcampuswien.masterprojekt.entity.Product;
import com.fhcampuswien.masterprojekt.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
}
