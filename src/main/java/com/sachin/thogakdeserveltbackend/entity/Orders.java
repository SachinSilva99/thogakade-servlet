package com.sachin.thogakdeserveltbackend.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity(name = "orders")
@Table(name = "orders")
public class Orders implements SuperEntity{
    @Id
    private String orderId;
    @Column(nullable = false)
    private Date date;

    @ManyToOne(cascade = CascadeType.ALL)
    private Customer customer;

    @OneToMany(mappedBy = "orders", cascade = CascadeType.ALL)
    private List<OrderDetail> orderDetails = new ArrayList<>();
}
