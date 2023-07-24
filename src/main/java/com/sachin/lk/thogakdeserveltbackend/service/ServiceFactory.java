package com.sachin.lk.thogakdeserveltbackend.service;


import com.sachin.lk.thogakdeserveltbackend.service.custom.impl.CustomerServiceImpl;
import com.sachin.lk.thogakdeserveltbackend.service.custom.impl.ItemServiceImpl;
import com.sachin.lk.thogakdeserveltbackend.service.custom.impl.OrderServiceImpl;
import com.sachin.lk.thogakdeserveltbackend.service.custom.impl.PlaceOrderServiceImpl;

public class ServiceFactory {
    private static final ServiceFactory SERVICE_FACTORY = new ServiceFactory();
    public static ServiceFactory getInstance(){
        return SERVICE_FACTORY;
    }
    public <T extends SuperService>T getService(ServiceType serviceType){
        switch (serviceType){
            case CUSTOMER:
                return (T) new CustomerServiceImpl();
            case ITEM:
                return (T) new ItemServiceImpl();
            case PLACE_ORDER:
                return (T) new PlaceOrderServiceImpl();
            case ORDER:
                return (T) new OrderServiceImpl();
            default:
                return null;
        }
    }
}
