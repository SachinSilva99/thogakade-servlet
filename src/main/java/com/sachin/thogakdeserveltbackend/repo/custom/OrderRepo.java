package com.sachin.thogakdeserveltbackend.repo.custom;


import com.sachin.thogakdeserveltbackend.entity.Orders;
import com.sachin.thogakdeserveltbackend.repo.CrudRepo;
import org.hibernate.Session;

public interface OrderRepo extends CrudRepo<Orders, String> {
    String getLastOrderId(Session session);
}
