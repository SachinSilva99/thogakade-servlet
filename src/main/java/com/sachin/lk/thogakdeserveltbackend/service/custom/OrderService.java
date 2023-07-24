package com.sachin.lk.thogakdeserveltbackend.service.custom;

import lk.ijse.thogakade.service.SuperService;

public interface OrderService extends SuperService {
      String generateNextOrderId();
      String generateNextOrderId(String currentOrderId);
}
