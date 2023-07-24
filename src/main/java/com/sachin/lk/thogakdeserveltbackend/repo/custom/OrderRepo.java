package com.sachin.lk.thogakdeserveltbackend.repo.custom;


import com.sachin.lk.thogakdeserveltbackend.entity.Orders;
import com.sachin.lk.thogakdeserveltbackend.repo.CrudRepo;
import org.hibernate.Session;

public interface OrderRepo extends CrudRepo<Orders, String> {
    String getLastOrderId(Session session);
}
