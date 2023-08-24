package com.root.dbservice.entities;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "Product")
@Data
public class ProductEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "productId")
    private Long productId;

    @Column(name = "productName")
    private String productName;

    @Column(name = "productSeries")
    private String productSeries;

    @Column(name = "productOpeningStock")
    private int productOpeningStock = 0;

}
