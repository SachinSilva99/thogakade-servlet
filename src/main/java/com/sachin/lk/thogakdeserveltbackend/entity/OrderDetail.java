package com.sachin.lk.thogakdeserveltbackend.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "orderdetail")
public class OrderDetail implements SuperEntity{
    @Id()
    @ManyToOne()
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(
            name = "orderId"
    )
    private Orders orders;

    @Id
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(
            name = "itemCode"
    )
    private Item item;

    @Column(nullable = false)
    private int qty;

    @Column(nullable = false)
    private double unitPrice;
}
