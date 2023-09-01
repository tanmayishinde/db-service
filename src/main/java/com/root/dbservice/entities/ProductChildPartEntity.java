package com.root.dbservice.entities;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "ProductChildPartRelationship")
public class ProductChildPartEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private Long id;

    @Column(name = "ProductId")
    private Long productId;

    @Column(name = "ChildPartId")
    private Long childPartId;

    @Column(name = "Quantity")
    private int quantity;
}
