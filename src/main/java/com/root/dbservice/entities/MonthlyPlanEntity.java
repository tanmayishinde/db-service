package com.root.dbservice.entities;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;

@Entity
@Data
@Table(name = "MonthlyPlan")
public class MonthlyPlanEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private Long id;

    @Column(name = "ProductId")
    private Long productId;

    @Column(name = "Week1")
    private int week1;

    @Column(name = "Week2")
    private int  week2;

    @Column(name = "Week3")
    private int week3;

    @Column(name = "Week4")
    private int  week4;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "CreationDate")
    private Date creationDate;

    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "UpdationDate")
    private Date updationDate;
}
