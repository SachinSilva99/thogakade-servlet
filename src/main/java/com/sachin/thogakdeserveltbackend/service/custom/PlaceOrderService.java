package com.sachin.thogakdeserveltbackend.service.custom;


import com.sachin.thogakdeserveltbackend.dto.CartDetailDTO;
import com.sachin.thogakdeserveltbackend.dto.CustomerDTO;
import com.sachin.thogakdeserveltbackend.dto.OrderDTO;
import com.sachin.thogakdeserveltbackend.service.SuperService;

import java.util.ArrayList;

public interface PlaceOrderService extends SuperService {

    boolean placeOrder(OrderDTO orderDTO, ArrayList<CartDetailDTO> cartDetailDTOS, CustomerDTO customerDTO);
}
