package com.fhcampuswien.masterprojekt.entity;

import com.fhcampuswien.masterprojekt.Enum.ProductStatus;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Product {
    @Id
    @GeneratedValue(strategy =  GenerationType.SEQUENCE)
    @Column(nullable = false, updatable = false)
    private Long productId;

    @Column(nullable = false)
    private String productName;

    @Column(nullable = false)
    private String productDescription;

    @Column(nullable = false)
    private double price;

    @Column
    private ProductStatus status;

    @Column
    private Long boughtByUser;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "user_id")
    private User user;
}
