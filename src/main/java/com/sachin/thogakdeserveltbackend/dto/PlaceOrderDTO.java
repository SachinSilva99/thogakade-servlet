package com.sachin.thogakdeserveltbackend.dto;

/*
    @author DanujaV
    @created 11/15/22 - 11:46 PM   
*/


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
@AllArgsConstructor
@NoArgsConstructor
@Data
public class PlaceOrderDTO {
    private CustomerDTO customerDto;
    private String orderId;
    private ArrayList<CartDetailDTO> cartDetailDTOS = new ArrayList<>();
}
