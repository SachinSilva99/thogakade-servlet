package com.sachin.lk.thogakdeserveltbackend.service.custom;


import com.sachin.lk.thogakdeserveltbackend.service.SuperService;

public interface OrderService extends SuperService {
      String generateNextOrderId();
      String generateNextOrderId(String currentOrderId);
}
