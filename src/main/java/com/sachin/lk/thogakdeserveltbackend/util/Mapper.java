package com.sachin.lk.thogakdeserveltbackend.util;

import lk.ijse.thogakade.dto.CustomerDTO;
import lk.ijse.thogakade.dto.ItemDTO;
import lk.ijse.thogakade.dto.OrderDTO;
import lk.ijse.thogakade.entity.Customer;
import lk.ijse.thogakade.entity.Item;
import lk.ijse.thogakade.entity.Orders;

import java.util.ArrayList;

public class Mapper {
    public Customer toCustomer(CustomerDTO customerDTO){
        return new Customer(
                customerDTO.getId(),
                customerDTO.getName(),
                customerDTO.getAddress(),
                customerDTO.getSalary(),
                new ArrayList<>());
    }
    public CustomerDTO toCustomerDTO(Customer customer){
        return new CustomerDTO(
                customer.getId(),
                customer.getName(),
                customer.getAddress(),
                customer.getSalary()
        );
    }
    public Item toItem(ItemDTO itemDTO){
        return new Item(
                itemDTO.getCode(),
                itemDTO.getDescription(),
                itemDTO.getUnitPrice(),
                itemDTO.getQtyOnHand(),
                new ArrayList<>());
    }
    public ItemDTO toItemDTO(Item item){
        return new ItemDTO(
                item.getCode(),
                item.getDescription(),
                item.getUnitPrice(),
                item.getQtyOnHand()
        );
    }
    public OrderDTO toOrderDto(Orders orders){
        return new OrderDTO(
                orders.getOrderId(),
                orders.getDate(),
                orders.getCustomer().getId()
        );
    }
    public Orders toOrders(OrderDTO orderDTO){
        return new Orders(orderDTO.getOrderId(),orderDTO.getDate(),null,null);
    }
}
