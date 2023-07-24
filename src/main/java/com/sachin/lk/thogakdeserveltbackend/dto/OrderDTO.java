package com.sachin.lk.thogakdeserveltbackend.dto;

/*
    @author DanujaV
    @created 11/15/22 - 11:53 PM   
*/

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class OrderDTO {
    private String orderId;
    private Date date;
    private String customerId;
}
