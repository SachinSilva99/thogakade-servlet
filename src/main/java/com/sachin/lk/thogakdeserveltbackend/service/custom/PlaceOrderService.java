package com.sachin.lk.thogakdeserveltbackend.service.custom;


import com.sachin.lk.thogakdeserveltbackend.dto.CartDetailDTO;
import com.sachin.lk.thogakdeserveltbackend.dto.CustomerDTO;
import com.sachin.lk.thogakdeserveltbackend.dto.OrderDTO;
import com.sachin.lk.thogakdeserveltbackend.service.SuperService;

import java.util.ArrayList;

public interface PlaceOrderService extends SuperService {

    boolean placeOrder(OrderDTO orderDTO, ArrayList<CartDetailDTO> cartDetailDTOS, CustomerDTO customerDTO);
}
