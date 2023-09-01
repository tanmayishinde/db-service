package com.root.dbservice.entities;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "ChildPart")
@Data
public class ChildPartEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "childPartId")
    private Long childPartId;

    @Column(name = "childPartName")
    private String childPartName;

    @Column(name = "childPartSeries")
    private String childPartSeries;

    @Column(name = "childPartOpeningStock")
    private int childPartOpeningStock = 0;

}
