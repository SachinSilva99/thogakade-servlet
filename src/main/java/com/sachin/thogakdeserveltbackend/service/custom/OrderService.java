package com.sachin.thogakdeserveltbackend.service.custom;


import com.sachin.thogakdeserveltbackend.dto.OrderDTO;
import com.sachin.thogakdeserveltbackend.dto.OrderDetailDTO;
import com.sachin.thogakdeserveltbackend.service.SuperService;

import java.util.List;

public interface OrderService extends SuperService {
      String getLastOrderId();

    List<OrderDetailDTO> getAllOrderDetails();

    List<OrderDTO> getAllOrders();
}
