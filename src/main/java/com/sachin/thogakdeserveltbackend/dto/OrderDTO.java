package com.sachin.thogakdeserveltbackend.dto;


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
