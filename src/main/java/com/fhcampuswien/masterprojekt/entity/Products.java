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
public class Products {
    @Id
    @GeneratedValue(strategy =  GenerationType.SEQUENCE)
    @Column(nullable = false, updatable = false)
    private Long productId;

    @Column(nullable = false)
    private String productname;

    @Column(nullable = false)
    private String productDescription;

    @Column(nullable = false)
    private double price;

    @Column (nullable = false)
    private ProductStatus status;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "user_id")
    private User user;
}
