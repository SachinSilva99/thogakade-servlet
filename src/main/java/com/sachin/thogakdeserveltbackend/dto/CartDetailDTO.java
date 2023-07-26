package com.sachin.thogakdeserveltbackend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@NoArgsConstructor
@Data
public class CartDetailDTO {
    private String orderId;
    private String code;
    private int qty;
    private String description;
    private double unitPrice;
    private int qtyOnHand;
    private ItemDTO itemDTO;
}
