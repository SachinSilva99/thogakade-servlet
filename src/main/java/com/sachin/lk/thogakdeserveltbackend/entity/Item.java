package com.sachin.lk.thogakdeserveltbackend.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "item")
public class Item implements SuperEntity {
    @Id
    private String code;
    @Column(nullable = false)
    private String description;
    @Column(nullable = false)
    private double unitPrice;
    @Column(nullable = false)
    private int qtyOnHand;

    @OneToMany(mappedBy = "item")
    private List<OrderDetail> orderDetails = new ArrayList<>();
}
