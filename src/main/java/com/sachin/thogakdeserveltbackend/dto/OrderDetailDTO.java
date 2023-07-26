package com.sachin.thogakdeserveltbackend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/*
Author : Sachin Silva
*/
@AllArgsConstructor
@NoArgsConstructor
@Data
public class OrderDetailDTO {
    private String orderId;
    private String itemCode;
    private String itemDes;
    private int qty;
    private double itemPrice;
}
